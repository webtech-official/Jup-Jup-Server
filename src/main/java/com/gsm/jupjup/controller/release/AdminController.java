package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.advice.exception.CDuplicateEmailException;
import com.gsm.jupjup.advice.exception.CEmailSigninFailedException;
import com.gsm.jupjup.advice.exception.EmailNotVerifiedException;
import com.gsm.jupjup.config.security.CustomUserDetailService;
import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.dto.admin.SignInDto;
import com.gsm.jupjup.dto.admin.SignUpDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.service.admin.AdminService;
import com.gsm.jupjup.service.email.EmailService;
import com.gsm.jupjup.util.RedisUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api(tags = {"회원 컨트롤러"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v2")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class AdminController {

    private String authKey_;
    private String refreshJwt = null;

    private final EmailService emailService;
    private final AdminRepo adminRepo;
    private final AdminService adminService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final CustomUserDetailService customUserDetailService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<Map<String, String>> signin(@ApiParam(value = "로그인 DTO", required = true) @RequestBody SignInDto signInDto,
                                                  HttpServletResponse res, HttpServletRequest req) {
        Admin admin = adminRepo.findByEmail(signInDto.getEmail()).orElseThrow(CEmailSigninFailedException::new);
        /*
        프론트에서 어짜피 회원가입 모달에서 이메일 체크가 됬는지 확인
         */
        if(admin.getRoles().contains("ROLE_NOT_PERMITTED")){
            throw new EmailNotVerifiedException();
        } else if (!passwordEncoder.matches(signInDto.getPassword(), admin.getPassword())) {
            throw new CEmailSigninFailedException();
        }

        String token = jwtTokenProvider.generateToken(admin);
        refreshJwt = jwtTokenProvider.generateRefreshToken(admin);
        redisUtil.setDataExpire(refreshJwt, admin.getUsername(), jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);

        Iterator<? extends GrantedAuthority> authorityIterator = admin.getAuthorities().iterator();
        String authority = authorityIterator.next().toString();
        Map<String ,String> map = new HashMap<>();
        map.put("AccessToken", token);
        map.put("RefreshToken", refreshJwt);
        map.put("authority", authority);
        map.put("name", admin.getName());
        map.put("classNum", admin.getClassNumber());
        map.put("email", admin.getEmail());
        return responseService.getSingleResult(map);
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "회원 가입 DTO", required = true) @RequestBody SignUpDto signUpDto) {
        //이메일 중복
        Optional<Admin> admin = adminRepo.findByEmail(signUpDto.getEmail());
        if(admin.isEmpty()){
            adminRepo.save(Admin.builder()
                    .email(signUpDto.getEmail())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .name(signUpDto.getName())
                    .classNumber(signUpDto.getClassNumber())
                    .roles(Collections.singletonList("ROLE_NOT_PERMITTED"))
                    .build());
        } else {
            throw new CDuplicateEmailException();
        }
        //임의의 authKey 생성 & 이메일 발송
        authKey_ = emailService.sendAuthMail(signUpDto.getEmail());
        log.info("이메일 보냄");
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원가입 이메일 인증", notes = "로그인을 위한 이메일 인증입니다.")
    @Transactional
    @GetMapping("member/signUpConfirm")
    public void signUpConfirm(@RequestParam String email, @RequestParam String AuthKey){
        if(authKey_.equals(AuthKey)){
            Admin admin = adminRepo.findByEmail(email).orElseThrow(CEmailSigninFailedException::new);
            admin.Change_ROLE_USER();
        } else {
            System.out.println("인증번호가 올바르지 않습니다.");
        }
    }


    @ApiOperation(value = "로그아웃", notes = "사용자가 로그아웃한다.")
    @PostMapping("/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult LogOut(HttpServletResponse res){
        redisUtil.deleteData(refreshJwt);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원 정보", notes = "회원 정보를 조회한다.")
    @GetMapping("/userinfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public SingleResult<Admin> UserInfo(){
        Admin admin = adminService.UserInfo();
        return responseService.getSingleResult(admin);
    }

    @ApiOperation(value = "새로운 토큰 요청하기", notes = "유저가 비밀번호를 변경한다.")
    @ResponseBody
    @PostMapping("/auth/refresh")
    public void authRefresh(String refreshJwt, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String newAccessToken = null;
        String RefreshTokenUserEmail = jwtTokenProvider.getUserEmail(refreshJwt);
        String RedisRefreshJwt = redisUtil.getData(RefreshTokenUserEmail);  //현재 Redis에 저장되어 있는 리프레쉬 토큰

        if(RedisRefreshJwt.equals(refreshJwt)){
            UserDetails userDetails = customUserDetailService.loadUserByUsername(RefreshTokenUserEmail);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            Admin admin = customUserDetailService.findAdmin( RefreshTokenUserEmail);
            newAccessToken = jwtTokenProvider.generateToken(admin);
            httpServletResponse.addHeader("newAccessToken", newAccessToken);
        }
    }
}