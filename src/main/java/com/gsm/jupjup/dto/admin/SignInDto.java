package com.gsm.jupjup.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInDto {

    private String email;
    private String password;

}
