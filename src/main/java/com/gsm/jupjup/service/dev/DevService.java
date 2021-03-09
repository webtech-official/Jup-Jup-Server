package com.gsm.jupjup.service.dev;

import com.gsm.jupjup.model.Admin;

import java.util.List;

public interface DevService {

    //Admin으로 바꾸는 메소드
    void changeAdmin(Long admin_Idx);

    //유저 전체 검색
    List<Admin> findAllAdmin();

}
