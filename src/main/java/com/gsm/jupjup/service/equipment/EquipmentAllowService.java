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
    public void save(String NameOfEquipment, EquipmentAllowSaveDto equipmentAllowSaveDto) throws Exception {
        //기자제 이름으로 equipment 테이블을 조회
        Equipment equipment = equipmentService.equipmentFindBy(NameOfEquipment);
        //equipment 조회해서 dto에 넣어줌
        equipmentAllowSaveDto.setEquipment(equipment);
        EquipmentAllow equipmentAllow = equipmentAllowSaveDto.toEntity();
        //기자제 남은 수량 계산
        //int equipmentAmount = equipment.getCount() - equipmentAllow.getAmount();

        equipmentAllowRepo.save(equipmentAllow);
        //기자재 수량 업데이트
        //equipment.updateAmount(equipmentAmount);
    }

    @Transactional
    public void update(Long eqa_idx, EquipmentAllowSaveDto equipmentAllowSaveDto){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_idx);
        int equipmentCount = equipmentAllow.getAmount();
        int equipmentAllowAmount = equipmentAllow.getAmount();

        if(equipmentAmountCountChk(equipmentCount, equipmentAllowAmount))
            equipmentAllow.update(equipmentAllow.getAmount(), equipmentAllow.getReason());
        else
            throw new IllegalArgumentException("현제 신청할 수 있는 기자제를 초과 했습니다.")


        //equipmentAllow.update(newEquipmentAllowAmount, equipmentAllowSaveDto.getReason(), UpdateEquipmentCount);
    }

    public EquipmentAllow equipmentAllowFindBy(Long idx){
        return equipmentAllowRepo.findById(idx).orElseThrow(IllegalAccessError::new);
    }

    //equipmentAllow
    public boolean equipmentAmountCountChk(int equipmentCount, int newEquipmentAllowAmount){
        if(equipmentCount >= newEquipmentAllowAmount)
            return true;
        else
            return false;
    }


}