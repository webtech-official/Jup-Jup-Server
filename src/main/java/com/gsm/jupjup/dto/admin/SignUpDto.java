package com.gsm.jupjup.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpDto {

    private String email;
    private String password;
    private String name;
    private String classNumber;

}
