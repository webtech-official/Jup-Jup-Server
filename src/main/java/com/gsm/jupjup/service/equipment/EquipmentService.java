package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.advice.exception.EquipmentNotFoundException;
import com.gsm.jupjup.advice.exception.ImageNotFoundException;
import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public void update(String name, int count) throws Exception {
        Equipment equipment = equipmentFindBy(name);
        equipment.update(count);
    }

    @Transactional
    public void deleteByName(String name) throws Exception {
        String equipmentName = equipmentFindBy(name).getName();
        equipmentRepo.deleteAllByName(equipmentName);
    }

    @Transactional(readOnly = true)
    public EquipmentResDto findByIdx(String name) throws Exception {
        Equipment equipment = equipmentFindBy(name);
        return new EquipmentResDto(equipment);
    }

    //Equipment를 name으로 찾고 Entity만드는 매서드
    public Equipment equipmentFindBy(String name) throws Exception {
        return equipmentRepo.findByName(name).orElseThrow(EquipmentNotFoundException::new);
    }

    //기자재 중복 처리 메소드

}
