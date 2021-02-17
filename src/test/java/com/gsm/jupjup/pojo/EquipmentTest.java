package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.Equipment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EquipmentTest {

    @Test
    @DisplayName("Equipment Domain Test")
    public void EquipmentTest() throws Exception {
        //given
        Equipment equipment = Equipment.builder()
                .name("맥북")
                .content("노트북")
                .count(1000)
                .img_equipment("image")
                .build();

        //when
        String name = equipment.getName();
        int count = equipment.getCount();

        //then
        assertThat(name).isEqualTo("맥북");
        assertThat(count).isEqualTo(1000);
    }

}
