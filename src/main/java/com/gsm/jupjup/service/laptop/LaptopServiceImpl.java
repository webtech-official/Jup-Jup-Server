package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.advice.exception.CUserNotFoundException;
import com.gsm.jupjup.advice.exception.NotFoundLaptopException;
import com.gsm.jupjup.advice.exception.NotFoundLaptopSpecException;
import com.gsm.jupjup.dto.laptop.LaptopSaveReqDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LaptopServiceImpl implements LaptopService{

    private final LaptopRepo laptopRepo;
    private final LaptopSpecRepo laptopSpecRepo;
    private final AdminRepo adminRepo;

    /**
     * 노트북 저장
     * @param laptopSaveReqDto 노트북 저장 정보
     * @return 노트북 시리얼 번호
     */
    @Override
    public String save(LaptopSaveReqDto laptopSaveReqDto){
        Admin admin = adminRepo.findByEmail(currentUser().getEmail()).orElseThrow(CUserNotFoundException::new);
        LaptopSpec laptopSpec = laptopSpecRepo.findById(laptopSaveReqDto.getSpecIdx()).orElseThrow(NotFoundLaptopSpecException::new);
        //Laptop 도매인 객체 만들기
        Laptop laptop = laptopSaveReqDto.toEntity(admin, laptopSpec);
        //Success, return LaptopName
        return laptopRepo.save(laptop).getLaptopSerialNumber();
    }

    /**
     * 해당 노트북 삭제
     * @param laptopSerialNumber 노트북 시리얼 번호
     */
    @Transactional
    @Override
    public void delete(String laptopSerialNumber){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(NotFoundLaptopException::new);
        laptopRepo.delete(laptop);
    }

    /**
     * 해당 노트북 검색
     * @param laptopSerialNumber 노트북 시리얼 번호
     * @return
     */
    @Override
    public Laptop findByLaptopSerialNumber(String laptopSerialNumber){
        return laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(NotFoundLaptopException::new);
    }

    /**
     * 모든 노트북 검색
     * @return List<Laptop>
     */
    //모두 찾기
    @Override
    public List<Laptop> findAll(){
        return laptopRepo.findAllBy();
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
