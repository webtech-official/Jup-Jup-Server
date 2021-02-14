package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.dto.equipmentAllow.EquipmentAllowSaveDto;
import com.gsm.jupjup.model.EquipmentAllow;

public interface EquipmentAllowService {
    //기자재 신청
    void save(String NameOfEquipment, EquipmentAllowSaveDto equipmentAllowSaveDto);

    //신청 idx 검색
    EquipmentAllow findById(Long eqa_idx);

    //
    EquipmentAllow equipmentAllowFindBy(Long idx);

    //승인 요청 처리
    void SuccessAllow(Long eqa_Idx);

    //승인 거절 처리
    void FailureAllow(Long eqa_Idx);

    //반납 요청 처리
    void ReturnAllow(Long eqa_Idx);
}
