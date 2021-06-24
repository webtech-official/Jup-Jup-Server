package com.gsm.jupjup.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPasswordChangeDto {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$", message = "숫자, 문자, 특수문자 모두 포함 (8~15자)로 입력해주세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String memberPassword;

}