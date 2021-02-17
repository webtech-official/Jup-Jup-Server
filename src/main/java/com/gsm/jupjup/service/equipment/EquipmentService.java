package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;

import java.io.IOException;
import java.util.List;

public interface EquipmentService {
    //기자재 저장
    void save(EquipmentUploadDto equipmentUploadDto) throws IOException;

    //수량 업데이트
    void update(String name, int count);

    //기자재 전부 업데이트
    void AllUpdate(String oldName, EquipmentUploadDto equipmentUploadDto) throws IOException;

    //기자재 이름 삭제
    void deleteByEquipmentIdx(Long idx);

    //기자재 이름 검색
    EquipmentResDto findByName(String name) throws IOException;

    //기자재 모두 검색
    List<Equipment> findAll() throws IOException;

    //기자재 이름 검색
    Equipment equipmentFindBy(String name);

    //기자재 키워드 검색
    List<Equipment> findByKeyword(String keyword) throws Exception;

}
