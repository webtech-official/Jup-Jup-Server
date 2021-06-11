package com.gsm.jupjup.dto.category;

import com.gsm.jupjup.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveDto {

    @NotBlank(message = "카테고리 이름을 입력해주세요.")
    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .categoryName(this.categoryName)
                .build();
    }

}
