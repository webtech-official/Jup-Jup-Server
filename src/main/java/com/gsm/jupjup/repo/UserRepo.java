package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
