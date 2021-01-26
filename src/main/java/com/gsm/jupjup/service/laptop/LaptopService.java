package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.dto.laptop.LaptopResponseDto;
import com.gsm.jupjup.dto.laptop.LaptopSaveRequestDto;
import com.gsm.jupjup.dto.laptop.LaptopUpdateRequestDto;
import com.gsm.jupjup.dto.laptopSpec.LaptopSpecSaveRequestDto;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.repo.LaptopRepo;
import com.gsm.jupjup.repo.LaptopSpecRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LaptopService {
    // DI
    @Autowired
    private LaptopRepo laptopRepo;

    @Autowired
    private LaptopSpecRepo laptopSpecRepo;

    @Transactional
    public String save(LaptopSaveRequestDto laptopSaveRequestDto){
        LaptopSpec laptopSpec = laptopSpecRepo.findBySpecIdx(laptopSaveRequestDto.getSpecIdx());

        //Laptop save 넣기
        Laptop laptop = Laptop.builder()
                .laptopSerialNumber(laptopSaveRequestDto.getLaptopSerialNumber())
                .laptopName(laptopSaveRequestDto.getLaptopName())
                .laptopBrand(laptopSaveRequestDto.getLaptopBrand())
                .laptopSpec(laptopSpec)
                .build();
        //Success, return LaptopName
        return laptopRepo.save(laptop).getLaptopName();
    }

    @Transactional
    public String update(String laptopSerialNumber, LaptopUpdateRequestDto laptopSaveRequestDto){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(() -> new IllegalArgumentException("해당 노트북이 없습니다:"+laptopSerialNumber));
        laptop.update(laptopSaveRequestDto.getLaptopName(), laptopSaveRequestDto.getLaptopBrand());
        // 성공시 laptopSerialNumber을 반환.
        return laptopSerialNumber;
    }

    @Transactional
    public void delete(String laptopSerialNumber){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(() -> new IllegalArgumentException("해당 노트북이 없습니다"+laptopSerialNumber));
        laptopRepo.delete(laptop);
    }

    @Transactional(readOnly = true)
    public LaptopResponseDto findByLaptopSerialNumber(String laptopSerialNumber){
        Laptop laptop = laptopRepo.findByLaptopSerialNumber(laptopSerialNumber).orElseThrow(() -> new IllegalArgumentException("해당 노트북이 없습니다:"+laptopSerialNumber));
        return new LaptopResponseDto(laptop);
    }
}
