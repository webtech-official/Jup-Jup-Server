package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.Admin;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminTest {

    @Test
    public void AdminTest() throws Exception {
        //given
        Admin admin = Admin.builder()
                .email("s19066@gsm.hs.kr")
                .classNumber("2101")
                .name("김상현")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        //when
        String email = admin.getEmail();
        List<String> roles = admin.getRoles();

        //then
        assertThat(admin.getEmail()).isEqualTo("s19066@gsm.hs.kr");
        assertThat(admin.getRoles()).contains("ROLE_USER");
    }

}
