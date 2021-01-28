package com.gsm.jupjup.service.mypage;

import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.repo.LaptopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageService {
    final AdminRepo adminRepo;
    final EquipmentAllowRepo equipmentAllowRepo;
    final JwtTokenProvider jwtTokenProvider;
    final LaptopRepo laptopRepo;

    //내가 빌린 기자재 보기
    public List<EquipmentAllow> findMyEquipment(HttpServletRequest req){
        //이메일 구하기
        String userEmail = GetUserEmail(req);
        Admin admin = adminRepo.findByEmail(userEmail).orElseThrow(null);

        //빌린 기자재 보기
        return equipmentAllowRepo.findByAdmin(admin);
    }

    public String GetUserEmail(HttpServletRequest req){
        String token = jwtTokenProvider.resolveToken(req);
        String userEmail = jwtTokenProvider.getUserPk(token);
        return userEmail;
    }

    //현재 나의 노트북 보기
    public List<Laptop> findMyLaptop(HttpServletRequest req){
        //이메일 구하기
        String userEmail = GetUserEmail(req);
        Admin admin = adminRepo.findByEmail(userEmail).orElseThrow(null);

        return laptopRepo.findByAdmin(admin);
    }

}
