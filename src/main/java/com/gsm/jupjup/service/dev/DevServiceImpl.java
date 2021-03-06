package com.gsm.jupjup.service.dev;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DevServiceImpl implements DevService{

    private final AdminRepo adminRepo;

    /**
     * 어드민으로 변경하기
     * @param admin_Idx 회원 번호
     */
    @Transactional
    @Override
    public void changeAdmin(Long admin_Idx) {
        Admin admin = adminRepo.findById(admin_Idx).orElseThrow(null);
        //어드민 바꾸기
        admin.setRoles(Collections.singletonList("ROLE_ADMIN"));
    }

    /**
     * 유저 검색하기
     */
    @Override
    public List<Admin> findAllAdmin() {
        List<Admin> admin = adminRepo.findAll();
        return admin;
    }


}
