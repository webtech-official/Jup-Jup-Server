package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentAllowRepo extends JpaRepository<EquipmentAllow, Long> {
    List<EquipmentAllow> findByAdmin(Admin admin);

    List<EquipmentAllow> findAllBy();

    List<EquipmentAllow> findByEquipment(Equipment equipment);
}
