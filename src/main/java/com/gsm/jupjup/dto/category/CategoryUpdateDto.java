package com.gsm.jupjup.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {

    @NotBlank(message = "카테고리 이름을 입력해주세요.")
    private String categoryName;

}
