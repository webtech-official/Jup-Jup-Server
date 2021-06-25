package com.gsm.jupjup.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshDto {

    @NotBlank(message = "ReFreshToken을 입력해주세요.")
    String reFreshToken;

}
