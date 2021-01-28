package com.gsm.jupjup.config.exceptionhandler;

import com.gsm.jupjup.advice.exception.CUserNotFoundException;
import com.gsm.jupjup.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AdminRepo adminRepo;

    public UserDetails loadUserByUsername(String email) {
        return adminRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);
    }
}