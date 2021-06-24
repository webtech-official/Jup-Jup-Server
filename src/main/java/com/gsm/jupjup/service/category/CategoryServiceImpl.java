package com.gsm.jupjup.service.category;

import com.gsm.jupjup.dto.category.CategorySaveDto;
import com.gsm.jupjup.dto.category.CategoryUpdateDto;
import com.gsm.jupjup.model.Category;
import com.gsm.jupjup.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void saveCategory(CategorySaveDto categorySaveDto) throws Exception {
        categoryDuplicateCheck(categorySaveDto.getCategoryName());
        categoryRepository.save(categorySaveDto.toEntity());
    }

    private void categoryDuplicateCheck(String name) throws Exception {
        Optional<Category> byCategoryName = categoryRepository.findByCategoryName(name);
        if(byCategoryName.isEmpty()){
            return;
        } else {
            throw new Exception("카테고리 중복");
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteIdx(Long idx) {
        categoryRepository.deleteById(idx);
    }

    @Override
    @Transactional
    public void updateIdx(CategoryUpdateDto categoryUpdateDto, Long idx) {
        Category byCategoryIdx = categoryRepository.findByCategoryIdx(idx);
        byCategoryIdx.change_CategoryName(categoryUpdateDto.getCategoryName());
    }
}
