package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.EquipmentAllow;

import java.util.List;

public interface AdminService {

    //신청 모두 조회
    List<EquipmentAllow> findAll();

}
