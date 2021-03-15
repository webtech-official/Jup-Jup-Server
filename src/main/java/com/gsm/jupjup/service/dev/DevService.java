package com.gsm.jupjup.service.dev;

import com.gsm.jupjup.model.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DevService {

    /**
     * 어드민으로 변경하기
     */
    void changeAdmin(Long admin_Idx);

    /**
     * 유저 검색하기
     */
    List<Admin> findAllAdmin();


}
