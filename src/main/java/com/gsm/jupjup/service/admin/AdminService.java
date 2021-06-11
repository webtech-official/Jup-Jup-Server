package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.dto.admin.SignInDto;
import com.gsm.jupjup.dto.admin.SignInResDto;
import com.gsm.jupjup.dto.admin.SignUpDto;
import com.gsm.jupjup.model.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AdminService {
    //신청 모두 조회
    List<Object> findAll();

    //회원 조회
    Admin UserInfo();

    SignInResDto signIn(SignInDto signInDto);

    void signUp(SignUpDto signUpDto);

    void logOut();

    void authRefresh(String refreshJwt, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
