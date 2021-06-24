package com.gsm.jupjup.service.category;

import com.gsm.jupjup.dto.category.CategorySaveDto;
import com.gsm.jupjup.dto.category.CategoryUpdateDto;
import com.gsm.jupjup.model.Category;

import java.util.List;

public interface CategoryService {

    //카테고리 저장
    void saveCategory(CategorySaveDto categorySaveDto) throws Exception;

    //카테고리 모두 조회
    List<Category> findAll();

    //카테고리 삭제
    void deleteIdx(Long idx);

    //카테고리 이름 수정
    void updateIdx(CategoryUpdateDto categoryUpdateDto, Long idx);

}
