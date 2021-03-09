package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final EquipmentAllowRepo equipmentAllowRepo;
    private final EquipmentRepo equipmentRepo;

    /**
     * 신청 모두 조회
     * @return List<EquipmentAllow>
     */
    @Override
    public List<EquipmentAllow> findAll(){
        List<EquipmentAllow> equipmentAllow = equipmentAllowRepo.findAllBy();
        return equipmentAllow;
    }

    /**
     * 기자재 IDX 검색
     * @param idx 기자재 번호
     * @return Equipment
     */
    @Override
    public Equipment findByIdx(Long idx) {
        Equipment equipment = equipmentRepo.findById(idx).orElseThrow(null);
        return equipment;
    }
}
