package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecSaveRequestDto;
import com.gsm.jupjup.model.LaptopSpec;

import java.util.List;

public interface LaptopSpecService {

    //Spec 저장
    Long save(LaptopSpecSaveRequestDto laptopSpecSaveRequestDto);

    List<LaptopSpec> findAll();

    LaptopSpec findBySpecIdx(Long SpecIdx);

    void updateBySpecIdx(Long SpecIdx);

    void deleteBySpecIdx(Long SpecIdx);

}