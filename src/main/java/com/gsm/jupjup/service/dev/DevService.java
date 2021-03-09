package com.gsm.jupjup.service.dev;

import com.gsm.jupjup.model.Admin;

import java.util.List;

public interface DevService {

    void changeAdmin(Long admin_Idx);

    List<Admin> findAllAdmin();

}
