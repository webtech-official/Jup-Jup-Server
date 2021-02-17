package com.gsm.jupjup.pojo;

import com.gsm.jupjup.model.response.CommonResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CommonResultTest {

    @Test
    @DisplayName("응답 테스트")
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
        assertThat(commonResult.getCode()).isEqualTo(200);
        assertThat(commonResult.getMsg()).isEqualTo("성공하였습니다");
    }


}
