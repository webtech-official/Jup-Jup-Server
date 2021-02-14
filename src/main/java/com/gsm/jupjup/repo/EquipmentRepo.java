package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EquipmentRepo extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByName(String name);

    Optional<Equipment> deleteAllByName(String name);
}
