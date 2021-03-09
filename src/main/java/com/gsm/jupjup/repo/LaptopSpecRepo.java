package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.LaptopSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopSpecRepo extends JpaRepository<LaptopSpec, Long> {
    @Query("select l from LaptopSpec l where l.specIdx = :idx")
    LaptopSpec findBySpecIdx(Long idx);
}
