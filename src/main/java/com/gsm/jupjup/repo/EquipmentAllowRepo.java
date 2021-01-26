package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.ListResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentAllowRepo extends JpaRepository<EquipmentAllow, Long> {
    @Query("select e from EquipmentAllow e inner join fetch e.equipment")
    List<EquipmentAllow> findAllJoinFetch();


}
