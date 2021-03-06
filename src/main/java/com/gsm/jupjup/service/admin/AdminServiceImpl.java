package com.gsm.jupjup.service.admin;

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

    @Override
    public List<Object> findAll(){
        List<Object> equipmentAllow = equipmentAllowRepo.findAllBy();
        return equipmentAllow;
    }
}
