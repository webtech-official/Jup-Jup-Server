package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.repo.EquipmentRepo;
import com.gsm.jupjup.repo.mapping.EquipmentAllowMapping;
import com.gsm.jupjup.service.equipment.EquipmentAllowService;
import com.gsm.jupjup.service.equipment.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepo adminRepo;
    private final EquipmentAllowRepo equipmentAllowRepo;

    //신청 모두 조회
    public List<Object> findAll(){
        List<Object> equipmentAllow = equipmentAllowRepo.findAllBy();
        return equipmentAllow;
    }

    //반납하기

}
