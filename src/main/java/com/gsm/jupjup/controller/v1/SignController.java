package com.gsm.jupjup.controller.v1;

import com.gsm.jupjup.advice.exception.CDuplicateEmailException;
import com.gsm.jupjup.advice.exception.CEmailSigninFailedException;
import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.repo.AdminRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final AdminRepo adminRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        Admin admin = adminRepo.findByEmail(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, admin.getPassword()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(admin.getEmail()), admin.getRoles()));

    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name,
                               @ApiParam(value = "클래스", required = true) @RequestParam String classNumber) {
        //이메일 중복
        Optional<Admin> admin = adminRepo.findByEmail(id);
        if(admin.isEmpty()){
            adminRepo.save(Admin.builder()
                    .email(id)
                    .password(passwordEncoder.encode(password))
                    .name(name)
                    .classNumber(classNumber)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
        } else {
            throw new CDuplicateEmailException();
        }
        return responseService.getSuccessResult();
    }
}