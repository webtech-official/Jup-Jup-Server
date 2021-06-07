package com.gsm.jupjup.config.security;

import com.gsm.jupjup.advice.exception.CUserNotFoundException;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AdminRepo adminRepo;

    /**
     * 유저 정보를 가져오는 메서드
     * @param email
     */
    public UserDetails loadUserByUsername(String email) {
        return adminRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);
    }

    public Admin findAdmin(String email) {
        return adminRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);
    }
}