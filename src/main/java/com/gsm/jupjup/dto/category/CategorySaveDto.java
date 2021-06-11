package com.gsm.jupjup.dto.category;

import com.gsm.jupjup.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveDto {

    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .categoryName(this.categoryName)
                .build();
    }

}
