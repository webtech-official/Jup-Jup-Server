package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final EquipmentAllowRepo equipmentAllowRepo;

    /**
     * 신청 모두 조회
     * @return List<EquipmentAllow>
     */
    @Override
    public List<EquipmentAllow> findAll(){
        List<EquipmentAllow> equipmentAllow = equipmentAllowRepo.findAllBy();
        return equipmentAllow;
    }
}
