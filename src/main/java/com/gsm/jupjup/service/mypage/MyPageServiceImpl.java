package com.gsm.jupjup.service.mypage;

import com.gsm.jupjup.advice.exception.UserDoesNotExistException;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.repo.LaptopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MyPageServiceImpl implements MyPageService{

    final AdminRepo adminRepo;
    final EquipmentAllowRepo equipmentAllowRepo;
    final LaptopRepo laptopRepo;

    /**
     * 현재 사용자의 모든 빌린 기자재 검색
     * @return List<EquipmentAllow>
     */
    @Override
    public List<EquipmentAllow> findMyEquipment() {
        Admin admin = adminRepo.findByEmail(currentUser().getEmail()).orElseThrow(UserDoesNotExistException::new);
        return equipmentAllowRepo.findByAdmin(admin);
    }

    /**
     * 현재 사용자의 노트북 검색
     * @return List<Laptop>
     */
    @Override
    public List<Laptop> findMyLaptop(){
        Admin admin = adminRepo.findByEmail(currentUser().getEmail()).orElseThrow(UserDoesNotExistException::new);
        return laptopRepo.findByAdmin(admin);
    }

    /**
     * 현재 사용자 ID 검색
     * @return Admin
     */
    public static Admin currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin user = (Admin) authentication.getPrincipal();
        return user;
    }

    /**
     * 현재 사용자가 "ROLE_ADMIN"이라는 ROLE을 가지고 있는지 확인
     * @return boolean
     */
    public static boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }
}
