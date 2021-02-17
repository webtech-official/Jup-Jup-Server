package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class EquipmentAllowTest {
    
    @Test
    public void EquipmentAllowTest() throws Exception {
        //given
        Admin admin = Admin.builder()
                .email("s19066@gsm.hs.kr")
                .classNumber("2101")
                .name("김상현")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        Equipment equipment = Equipment.builder()
                .name("맥북")
                .content("노트북")
                .count(1000)
                .img_equipment("image")
                .build();
        EquipmentAllow equipmentAllow = EquipmentAllow.builder()
                .admin(admin)
                .equipment(equipment)
                .equipmentEnum(EquipmentAllowEnum.ROLE_Accept)
                .amount(3)
                .isReturn(false)
                .reason("String")
                .build();

        //when
        Admin findAdmin = equipmentAllow.getAdmin();
        Equipment findEquipment = equipmentAllow.getEquipment();
        EquipmentAllowEnum equipmentAllowEnum = equipmentAllow.getEquipmentEnum();

        //then
        assertThat(findAdmin).isInstanceOf(Admin.class);
        assertThat(findEquipment).isSameAs(equipment);
        assertThat(equipmentAllowEnum).isSameAs(EquipmentAllowEnum.ROLE_Accept);

    }
    
}
