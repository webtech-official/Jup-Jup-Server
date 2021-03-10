package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final EquipmentAllowRepo equipmentAllowRepo;
    private final AdminRepo adminRepo;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public List<Object> findAll(){
        List<Object> equipmentAllow = equipmentAllowRepo.findAllBy();
        return equipmentAllow;
    }

    @Override
    public Admin UserInfo() {
        String email = currentUser().getEmail();
        return adminRepo.findAllByEmail(email);
    }

    //현재 사용자의 ID를 Return
    public static Admin currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin user = (Admin) authentication.getPrincipal();
        return user;
    }
}
