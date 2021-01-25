package com.gsm.jupjup.service;

import com.gsm.jupjup.advice.exception.EquipmentNotFoundException;
import com.gsm.jupjup.advice.exception.ImageNotFoundException;
import com.gsm.jupjup.dto.EquipmentResDto;
import com.gsm.jupjup.dto.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class EquipmentService {

    private final EquipmentRepo equipmentRepo;

    /**
     * Equipment CRUD 구현
     * U => Dirty Checking
     */

    public void save(EquipmentUploadDto equipmentUploadDto) {
        if(equipmentUploadDto.getImg_equipment().isEmpty()) throw new ImageNotFoundException();

        Equipment equipmentDomain = equipmentUploadDto.toEntity();
        equipmentRepo.save(equipmentDomain);
    }

    @Transactional
    public void update(Long num, String name) throws Exception {
        Equipment equipment = equipmentFindBy(num);
        equipment.update(name);
    }

    public Long deleteByIdx(Long idx) throws Exception {
        equipmentFindBy(idx);
        equipmentRepo.deleteById(idx);
        return idx;
    }

    @Transactional(readOnly = true)
    public EquipmentResDto findByIdx(Long eq_Idx) throws Exception {
        Equipment equipment = equipmentFindBy(eq_Idx);
        return new EquipmentResDto(equipment);
    }

    //Equipment를 name으로 찾고 Entity만드는 매서드
    public Equipment equipmentFindBy(String name) throws Exception {
        return equipmentRepo.findByName(name).orElseThrow(EquipmentNotFoundException::new);
    }
    //Equipment를 idx으로 찾고 Entity만드는 매서드
    public Equipment equipmentFindBy(Long idx) throws Exception {
        return equipmentRepo.findById(idx).orElseThrow(EquipmentNotFoundException::new);
    }
}
