package com.gsm.jupjup.service.admin;

import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;

import java.util.List;

public interface AdminService {

    //신청 모두 조회
    List<EquipmentAllow> findAll();

    //기자재 IDX 검색
    Equipment findByIdx(Long idx);
}
