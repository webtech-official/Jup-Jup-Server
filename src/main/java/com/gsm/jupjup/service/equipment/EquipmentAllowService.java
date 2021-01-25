package com.gsm.jupjup.service.equipment;


import com.gsm.jupjup.dto.equipmentAllow.EquipmentAllowSaveDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EquipmentAllowService {
    public final EquipmentAllowRepo equipmentAllowRepo;
    public final EquipmentRepo equipmentRepo;
    public final EquipmentService equipmentService;

    @Transactional
    public void save(String NameOfEquipment , EquipmentAllowSaveDto equipmentAllowSaveDto) throws Exception {
        //기자제 이름으로 equipment 테이블을 조회
        Equipment equipment = equipmentService.equipmentFindBy(NameOfEquipment);
        //equipment 조회해서 dto에 넣어줌
        equipmentAllowSaveDto.setEquipment(equipment);
        EquipmentAllow equipmentAllow = equipmentAllowSaveDto.toEntity();
        //기자제 남은 수량 계산
        int equipmentAmount = equipment.getCount() - equipmentAllow.getAmount();

        equipmentAllowRepo.save(equipmentAllow);
        //기자재 수량 업데이트
        equipment.updateAmount(equipmentAmount);
    }
}
