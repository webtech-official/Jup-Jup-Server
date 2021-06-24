package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.dto.admin.*;
import com.gsm.jupjup.dto.email.MailDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.service.admin.AdminService;
import com.gsm.jupjup.service.email.SendEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"회원 컨트롤러"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v2")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final ResponseService responseService;
    private final SendEmailService sendEmailService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<SignInResDto> signin(@Valid @RequestBody SignInDto signInDto) {
        SignInResDto signInResDto = adminService.signIn(signInDto);
        return responseService.getSingleResult(signInResDto);
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping("/signup")
    @ResponseBody
    public CommonResult signup(@Valid @RequestBody SignUpDto signUpDto) {
        adminService.signUp(signUpDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "로그아웃", notes = "사용자가 로그아웃한다.")
    @PostMapping("/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    public CommonResult LogOut(){
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원 정보", notes = "회원 정보를 조회한다.")
    @GetMapping("/userinfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    public SingleResult<Admin> UserInfo(){
        Admin admin = adminService.UserInfo();
        return responseService.getSingleResult(admin);
    }

    @ApiOperation(value = "새로운 토큰 요청하기", notes = "유저가 비밀번호를 변경한다.")
    @ResponseBody
    @PostMapping("/auth/refresh")
    public SingleResult<Map<String, String>> AuthRefresh(@Valid @RequestBody AuthRefreshDto authRefreshDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String newToken = adminService.authRefresh(authRefreshDto.getReFreshToken(), httpServletResponse);
        httpServletResponse.addHeader("newToken", newToken);
        Map<String, String> map = new HashMap<>();
        map.put("newToken", newToken);
        return responseService.getSingleResult(map);
    }

    @ResponseBody
    @ApiOperation(value = "등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경", notes = "유저가 임시 비밀번호를 지급 받는다.")
    @PostMapping("/check/findPw/sendEmail")
    public CommonResult sendEmail(@RequestParam String userEmail){
        MailDto dto = sendEmailService.createMailAndChangePassword(userEmail);
        sendEmailService.mailSend(dto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "비밀번호 변경하기", notes = "유저가 비밀번호를 변경한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ResponseBody
    @PutMapping("/password-change")
    public CommonResult passwordChange(@Valid @RequestBody MemberPasswordChangeDto memberPasswordChangeDto) {
        adminService.change_password(memberPasswordChangeDto);
        return responseService.getSuccessResult();
    }
}