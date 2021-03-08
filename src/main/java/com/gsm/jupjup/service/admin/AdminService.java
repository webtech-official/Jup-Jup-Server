package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;

import java.util.List;

public interface AdminService {
    //신청 모두 조회
    List<EquipmentAllow> findAll();

    //EquipmentAllowEnum따라 조회
    List<EquipmentAllow> findByEquipmentAllowEnum(EquipmentAllowEnum equipmentAllowEnum);

    //기자재 신청 학생 검색
    List<EquipmentAllow> findByStudentName(String name);
}
