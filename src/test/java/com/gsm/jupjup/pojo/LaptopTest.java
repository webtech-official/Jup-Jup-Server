package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class LaptopTest {

    @Test
    @DisplayName("Laptop Domain Test")
    public void LaptopTest() throws Exception {
        //given
        Admin admin = Admin.builder()
                .email("s19066@gsm.hs.kr")
                .classNumber("2101")
                .name("김상현")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        LaptopSpec laptopSpec = LaptopSpec.builder()
                .CPU("intel-i7")
                .GPU("LadeonPro5600")
                .RAM("16gb")
                .SSD("256gb")
                .HDD("1tb")
                .build();
        Laptop laptop = Laptop.builder()
                .admin(admin)
                .laptopSpec(laptopSpec)
                .laptopName("삼성 노트북")
                .laptopBrand("삼성")
                .laptopSerialNumber("123")
                .classNumber("2101")
                .studentName("김상현")
                .build();
        
        //when
        Admin findAdmin = laptop.getAdmin();
        LaptopSpec findLapTopSpec = laptop.getLaptopSpec();
        String laptopSerialNumber = laptop.getLaptopSerialNumber();

        //then
        assertThat(findAdmin).isInstanceOf(Admin.class);
        assertThat(findLapTopSpec).isSameAs(findLapTopSpec);
        assertThat(laptopSerialNumber).isSameAs("123");
    }

}
