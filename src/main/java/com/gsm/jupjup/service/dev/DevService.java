package com.gsm.jupjup.service.dev;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class DevService {

    private final AdminRepo adminRepo;

    /**
     * 어드민으로 변경하기
     */
    @Transactional
    public void changeAdmin(Long admin_Idx) {
        Admin admin = adminRepo.findById(admin_Idx).orElseThrow(null);
        //어드민 바꾸기
        admin.setRoles(Collections.singletonList("ROLE_NOT_PERMITTED"));
    }

}
