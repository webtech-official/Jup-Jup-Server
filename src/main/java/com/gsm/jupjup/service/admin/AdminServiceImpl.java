package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminServiceImpl implements AdminService{

    private final EquipmentAllowRepo equipmentAllowRepo;

    /**
     * 기자재 신청 모두 찾기 => 관리자
     * @return equipmentAllow
     */
    @Override
    public List<EquipmentAllow> findAll(){
        List<EquipmentAllow> equipmentAllow = equipmentAllowRepo.findAllBy();
        return equipmentAllow;
    }

    /**
     * 기자재 거절 조회
     * @param equipmentAllowEnum 기자재의 상태 ROLE_Reject
     * @return equipmentAllow(ROLE_Reject)
     */
    @Override
    public List<EquipmentAllow> findByEquipmentAllowEnum(EquipmentAllowEnum equipmentAllowEnum) {
        List<EquipmentAllow> equipmentAllow = equipmentAllowRepo.findByEquipmentEnum(equipmentAllowEnum);
        return equipmentAllow;
    }

    /**
     * 기자재 신청 학생 이름 조회
     * @param name 학생 이름
     * @return EquipmentAllow
     */
    @Override
    public List<EquipmentAllow> findByStudentName(String name) {
        List<EquipmentAllow> equipmentAllows = equipmentAllowRepo.findByAdmin_Name(name);
        return equipmentAllows;
    }
}
