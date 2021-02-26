package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.advice.exception.CDuplicateEmailException;
import com.gsm.jupjup.advice.exception.CEmailSigninFailedException;
import com.gsm.jupjup.advice.exception.EmailNotVerifiedException;
import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.dto.admin.SignInDto;
import com.gsm.jupjup.dto.admin.SignUpDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.service.email.EmailService;
import com.gsm.jupjup.util.CookieUtil;
import com.gsm.jupjup.util.RedisUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Api(tags = {"1. 회원"})
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://192.168.0.25:3000") //해당 origin 승인하기
@RequestMapping(value = "/v2")
public class SignController {

    private String authKey_;
    private String refreshJwt = null;

    private final EmailService mss;
    private final AdminRepo adminRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

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
        Cookie accessToken = cookieUtil.createCookie(jwtTokenProvider.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtil.createCookie(jwtTokenProvider.REFRESH_TOKEN_NAME, refreshJwt);
        redisUtil.setDataExpire(refreshJwt, admin.getUsername(), jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
        res.addCookie(accessToken);
        res.addCookie(refreshToken);

        Map<String ,String> map = new HashMap<>();
        map.put("accessToken", token);
        return responseService.getSingleResult(map);
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "회원 가입 DTO", required = true) @Valid @RequestBody SignUpDto signUpDto) {
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
        authKey_ = mss.sendAuthMail(signUpDto.getEmail());

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원가입 이메일 인증", notes = "로그인을 위한 이메일 인증입니다.")
    @Transactional
    @GetMapping("member/signUpConfirm")
    public void signUpConfirm(@RequestParam String email, @RequestParam String AuthKey){
        if(authKey_.equals(AuthKey)){
            Admin admin = adminRepo.findByEmail(email).orElseThrow(CEmailSigninFailedException::new);
            admin.setRoles(Collections.singletonList("ROLE_USER"));
        } else {
            System.out.println("인증번호가 올바르지 않습니다.");
        }
    }


    @ApiOperation(value = "로그아웃", notes = "사용자가 로그아웃한다.")
    @GetMapping("/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult LogOut(HttpServletResponse res, @RequestHeader String Authorization){
        Cookie accessToken = cookieUtil.createCookie(jwtTokenProvider.ACCESS_TOKEN_NAME, null);
        accessToken.setMaxAge(0);
        redisUtil.deleteData(refreshJwt);
        Cookie refreshToken = cookieUtil.createCookie(jwtTokenProvider.REFRESH_TOKEN_NAME, null);
        refreshToken.setMaxAge(0);
        res.addCookie(accessToken);
        res.addCookie(refreshToken);
        return responseService.getSuccessResult();
    }

}