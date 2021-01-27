package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaptopRepo extends JpaRepository<Laptop, String> {
    // 명확한 error, exception 처리.
    Optional<Laptop> findByLaptopSerialNumber(String laptopSerialNumber);
}
