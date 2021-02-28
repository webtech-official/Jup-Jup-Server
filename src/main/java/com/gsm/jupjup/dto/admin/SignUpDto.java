package com.gsm.jupjup.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String email;
    private String password;
    private String name;
    private String classNumber;

}
