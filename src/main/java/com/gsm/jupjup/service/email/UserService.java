package com.gsm.jupjup.service.email;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AdminRepo adminRepo;

    public boolean userEmailCheck(String userEmail, String classNumber) {
        Optional<Admin> byMemberEmailAndMemberClassNumber = adminRepo.findByEmailAndClassNumber(userEmail, classNumber);
        if(byMemberEmailAndMemberClassNumber.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

}