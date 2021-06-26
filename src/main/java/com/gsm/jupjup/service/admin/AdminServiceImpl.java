package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.advice.exception.CAuthenticationEntryPointException;
import com.gsm.jupjup.advice.exception.CDuplicateEmailException;
import com.gsm.jupjup.advice.exception.CEmailSigninFailedException;
import com.gsm.jupjup.advice.exception.EmailNotVerifiedException;
import com.gsm.jupjup.security.CustomUserDetailService;
import com.gsm.jupjup.security.JwtTokenProvider;
import com.gsm.jupjup.dto.admin.MemberPasswordChangeDto;
import com.gsm.jupjup.dto.admin.SignInDto;
import com.gsm.jupjup.dto.admin.SignInResDto;
import com.gsm.jupjup.dto.admin.SignUpDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final EquipmentAllowRepo equipmentAllowRepo;
    private final AdminRepo adminRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public List<Object> findAll(){
        List<Object> equipmentAllow = equipmentAllowRepo.findAllBy();
        return equipmentAllow;
    }

    @Override
    public Admin UserInfo() {
        String email = GetUserEmail();
        return adminRepo.findAllByEmail(email);
    }

    @Override
    public SignInResDto signIn(SignInDto signInDto) {
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
        String refreshJwt = jwtTokenProvider.generateRefreshToken(admin);
        redisUtil.setDataExpire(refreshJwt, admin.getEmail(), jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
        System.out.println("리프레쉬 넣어줌");

        Iterator<? extends GrantedAuthority> authorityIterator = admin.getAuthorities().iterator();
        String authority = authorityIterator.next().toString();
        SignInResDto signInResDto = SignInResDto.builder()
                .accessToken(token)
                .refreshToken(refreshJwt)
                .name(admin.getName())
                .classNum(admin.getClassNumber())
                .authority(authority)
                .email(admin.getEmail())
                .build();
        return signInResDto;
    }

    @Override
    public void signUp(SignUpDto signUpDto) {
        //이메일 중복
        Optional<Admin> admin = adminRepo.findByEmail(signUpDto.getEmail());
        if(admin.isEmpty()){
            adminRepo.save(Admin.builder()
                    .email(signUpDto.getEmail())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .name(signUpDto.getName())
                    .classNumber(signUpDto.getClassNumber())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
        } else {
            throw new CDuplicateEmailException();
        }
    }

    @Override
    public void logOut() {
        String userEmail = GetUserEmail();
        redisUtil.deleteData(userEmail);
    }

    @Override
    public String authRefresh(String refreshJwt, HttpServletResponse httpServletResponse) {
        String newAccessToken = null;
        String RefreshTokenUserEmail = jwtTokenProvider.getUserEmail(refreshJwt);
        String RedisRefreshJwt = redisUtil.getData(RefreshTokenUserEmail);  //현재 Redis에 저장되어 있는 리프레쉬 토큰

        if(RedisRefreshJwt.equals(refreshJwt)){
            try{
                if(refreshJwt != null && jwtTokenProvider.validateToken(refreshJwt)){
                    Authentication authentication = jwtTokenProvider.getAuthentication(refreshJwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e){   // 만약 유효기간을 넘겼다면??
                httpServletResponse.setHeader("message", e.getMessage());
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            Admin member = customUserDetailService.findAdmin(RefreshTokenUserEmail);
            newAccessToken = jwtTokenProvider.generateToken(member);
        } else {
            System.out.println("같지 않다");
        }
        return newAccessToken;
    }

    @Transactional
    @Override
    public void change_password(MemberPasswordChangeDto memberPasswordChangeDto) {
        String userEmail = GetUserEmail();
        Admin member = adminRepo.findByEmail(userEmail).orElseThrow(CAuthenticationEntryPointException::new);
        String pw = passwordEncoder.encode(memberPasswordChangeDto.getMemberPassword());
        member.change_password(pw);
    }

    //현재 사용자의 ID를 Return
    public String GetUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails)principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }
}
