package com.gsm.jupjup.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResDto {

    private String accessToken;
    private String refreshToken;
    private String authority;
    private String name;
    private String classNum;
    private String email;

}
