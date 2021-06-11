package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.dto.admin.AuthRefreshDto;
import com.gsm.jupjup.dto.admin.SignInDto;
import com.gsm.jupjup.dto.admin.SignInResDto;
import com.gsm.jupjup.dto.admin.SignUpDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.service.admin.AdminService;
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

@Api(tags = {"회원 컨트롤러"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v2")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping("/signin")
    @ResponseBody
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
    public CommonResult authRefresh(@RequestBody AuthRefreshDto authRefreshDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        adminService.authRefresh(authRefreshDto.getReFreshToken(), httpServletRequest, httpServletResponse);
        return responseService.getSuccessResult();
    }
}