package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.advice.exception.NotFoundLaptopException;
import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.dto.laptop.LaptopResponseDto;
import com.gsm.jupjup.dto.laptop.LaptopSaveRequestDto;
import com.gsm.jupjup.dto.laptop.LaptopUpdateRequestDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.LaptopRepo;
import com.gsm.jupjup.repo.LaptopSpecRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Service
public class LaptopServiceImpl implements LaptopService{
    // DI
    @Autowired
    private LaptopRepo laptopRepo;

    @Autowired
    private LaptopSpecRepo laptopSpecRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public String save(LaptopSaveRequestDto laptopSaveRequestDto){
        Admin admin = adminRepo.findByEmail(currentUser().getEmail()).orElseThrow(null);
        LaptopSpec laptopSpec = laptopSpecRepo.findById(laptopSaveRequestDto.getSpecIdx()).orElseThrow(NotFoundLaptopException::new);
        //Laptop save 넣기
        Laptop laptop = Laptop.builder()
                .admin(admin)
                .laptopSerialNumber(laptopSaveRequestDto.getLaptopSerialNumber())
                .laptopName(laptopSaveRequestDto.getLaptopName())
                .laptopBrand(laptopSaveRequestDto.getLaptopBrand())
                .laptopSpec(laptopSpec)
                .studentName(laptopSaveRequestDto.getStudentName())
                .classNumber(laptopSaveRequestDto.getClassNumber())
                .build();
        //Success, return LaptopName
        return laptopRepo.save(laptop).getLaptopName();
    }

    @Transactional
    @Override
    public String update(String laptopSerialNumber, LaptopUpdateRequestDto laptopSaveRequestDto){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(NotFoundLaptopException::new);
        laptop.update(laptopSaveRequestDto.getLaptopName(), laptopSaveRequestDto.getLaptopBrand());
        // 성공시 laptopSerialNumber을 반환.
        return laptopSerialNumber;
    }

    @Transactional
    @Override
    public void delete(String laptopSerialNumber){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(NotFoundLaptopException::new);
        laptopRepo.delete(laptop);
    }

    @Override
    public LaptopResponseDto findByLaptopSerialNumber(String laptopSerialNumber){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(NotFoundLaptopException::new);
        return new LaptopResponseDto(laptop);
    }

    //모두 찾기
    @Override
    public List<Laptop> findAll(){
        return laptopRepo.findAll();
    }

    //현재 사용자의 ID를 Return
    public static Admin currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin user = (Admin) authentication.getPrincipal();
        return user;
    }

    //현재 사용자가 "ROLE_ADMIN"이라는 ROLE을 가지고 있는지 확인
    public static boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }
}
