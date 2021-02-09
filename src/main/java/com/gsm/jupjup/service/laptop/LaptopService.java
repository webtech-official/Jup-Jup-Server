package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.dto.laptop.LaptopResponseDto;
import com.gsm.jupjup.dto.laptop.LaptopSaveReqDto;
import com.gsm.jupjup.dto.laptop.LaptopUpdateReqDto;
import com.gsm.jupjup.model.Laptop;

import java.util.List;

public interface LaptopService {

    //노트북 저장
    String save(LaptopSaveReqDto laptopSaveReqDto);

    //노트북 정보 업데이트
    String update(String laptopSerialNumber, LaptopUpdateReqDto laptopSaveRequestDto);

    //노트북 삭제
    void delete(String laptopSerialNumber);

    //노트북 1개 조회
    LaptopResponseDto findByLaptopSerialNumber(String laptopSerialNumber);

    //노트북 모두 조회
    List<Laptop> findAll();

}
