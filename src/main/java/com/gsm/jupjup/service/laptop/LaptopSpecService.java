package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecDto;
import com.gsm.jupjup.model.LaptopSpec;

import java.util.List;

public interface LaptopSpecService {
    //Spec 저장
    Long save(LaptopSpecDto laptopSpecDto);

    List<LaptopSpec> findAll();

    LaptopSpec findBySpecIdx(Long SpecIdx);

    void updateBySpecIdx(Long SpecIdx, LaptopSpecDto laptopSpecDto);

    void deleteBySpecIdx(Long SpecIdx);

}