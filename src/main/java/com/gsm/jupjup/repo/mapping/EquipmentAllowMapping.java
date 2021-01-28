package com.gsm.jupjup.repo.mapping;

import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;

public interface EquipmentAllowMapping {

    Long getEqa_Idx();

    int getAmount();

    String getReason();

    EquipmentAllowEnum getEquipmentEnum();

    Equipment getEquipment();

}
