package com.gsm.jupjup.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    //응답 성공여부 : true/false
    @ApiModelProperty(value = "응답 성공여부")
    private boolean success;

    //>= 0 정상, < 0 비정상
    @ApiModelProperty(value = "응답 코드 번호")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;

}