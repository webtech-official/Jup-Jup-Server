package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.response.CommonResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CommonResultTest {

    @Test
    @DisplayName("CommonResult Domain Test")
    public void ResponseTest() throws Exception {
        //given
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(true);
        commonResult.setCode(200);
        commonResult.setMsg("성공하였습니다");

        //when
        int code = commonResult.getCode();
        String msg = commonResult.getMsg();

        //then
        assertThat(code).isEqualTo(200);
        assertThat(msg).isEqualTo("성공하였습니다");
    }


}
