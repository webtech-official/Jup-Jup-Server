package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.Admin;

import java.util.List;

public interface AdminService {
    //신청 모두 조회
    List<Object> findAll();

    //회원 조회
    Admin UserInfo(String Authorization);
}
