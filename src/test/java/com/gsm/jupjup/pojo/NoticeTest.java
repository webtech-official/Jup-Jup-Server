package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Notice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class NoticeTest {
    
    @Test
    @DisplayName("Notice Domain Test")
    public void NoticeTest() throws Exception {
        //given
        Admin admin = Admin.builder()
                .auth_Idx(1L)
                .email("s19066@gsm.hs.kr")
                .classNumber("2101")
                .name("김상현")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        Notice notice = Notice.builder()
                .adminIdx(admin.getAuth_Idx())
                .title("a")
                .content("a")
                .build();
        
        //when
        Long adminIdx = notice.getAdminIdx();
        String title = notice.getTitle();

        //then
        assertThat(adminIdx).isSameAs(1L);
        assertThat(title).isSameAs("a");
    }
    
}
