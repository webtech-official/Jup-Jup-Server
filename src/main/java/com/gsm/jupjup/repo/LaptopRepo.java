package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.model.response.ListResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LaptopRepo extends JpaRepository<Laptop, String> {
    // 명확한 error, exception 처리.
    Optional<Laptop> findByLaptopSerialNumber(String laptopSerialNumber);

    List<Laptop> findByAdmin(Admin admin);


}
