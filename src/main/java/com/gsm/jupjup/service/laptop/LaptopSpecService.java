package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecSaveRequestDto;

public interface LaptopSpecService {

    //Spec 저장
    Long save(LaptopSpecSaveRequestDto laptopSpecSaveRequestDto);

}