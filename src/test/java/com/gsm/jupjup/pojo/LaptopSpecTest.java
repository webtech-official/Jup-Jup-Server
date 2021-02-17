package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.LaptopSpec;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LaptopSpecTest {

    @Test@DisplayName("LaptopSpec Domain Test")
    public void LaptopSpecTest() throws Exception {
        //given
        LaptopSpec laptopSpec = LaptopSpec.builder()
                .CPU("intel-i7")
                .GPU("LadeonPro5600")
                .RAM("16gb")
                .SSD("256gb")
                .HDD("1tb")
                .build();

        //when
        String cpu = laptopSpec.getCPU();
        String ssd = laptopSpec.getSSD();

        //then
        assertThat(cpu).isSameAs("intel-i7");
        assertThat(ssd).isSameAs("256gb");
    }

}
