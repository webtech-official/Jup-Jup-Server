package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.advice.exception.CUserNotFoundException;
import com.gsm.jupjup.advice.exception.NotFoundLaptopException;
import com.gsm.jupjup.advice.exception.NotFoundLaptopSpecException;
import com.gsm.jupjup.dto.laptop.LaptopSaveReqDto;
import com.gsm.jupjup.dto.laptop.LaptopUpdateReqDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.LaptopRepo;
import com.gsm.jupjup.repo.LaptopSpecRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LaptopServiceImpl implements LaptopService{

    private final LaptopRepo laptopRepo;
    private final LaptopSpecRepo laptopSpecRepo;
    private final AdminRepo adminRepo;

    @Override
    public String save(LaptopSaveReqDto laptopSaveReqDto){
        Admin admin = adminRepo.findByEmail(GetUserEmail()).orElseThrow(CUserNotFoundException::new);
        LaptopSpec laptopSpec = laptopSpecRepo.findById(laptopSaveReqDto.getSpecIdx()).orElseThrow(NotFoundLaptopSpecException::new);
        //Laptop 도매인 객체 만들기
        Laptop laptop = laptopSaveReqDto.toEntity(admin, laptopSpec);
        //Success, return LaptopName
        return laptopRepo.save(laptop).getLaptopSerialNumber();
    }

    @Transactional
    @Override
    public void delete(String laptopSerialNumber){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(NotFoundLaptopException::new);
        laptopRepo.delete(laptop);
    }

    @Override
    public Laptop findByLaptopSerialNumber(String laptopSerialNumber){
        return laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(NotFoundLaptopException::new);
    }

    //모두 찾기
    @Override
    public List<Laptop> findAll(){
        return laptopRepo.findAllBy();
    }

    //현재 사용자의 ID를 Return
    public String GetUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails)principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }

    //현재 사용자가 "ROLE_ADMIN"이라는 ROLE을 가지고 있는지 확인
    public static boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }
}
