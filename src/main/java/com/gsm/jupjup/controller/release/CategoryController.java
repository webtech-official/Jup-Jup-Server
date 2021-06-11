package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.dto.category.CategorySaveDto;
import com.gsm.jupjup.dto.category.CategoryUpdateDto;
import com.gsm.jupjup.model.Category;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.category.CategoryService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"카테고리 등록 컨트롤러"})
@RestController
@RequestMapping("/v2")
@CrossOrigin(origins = "http://localhost:3000") //해당 origin 승인하기
@RequiredArgsConstructor
public class CategoryController {

    private final ResponseService responseService;
    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 등록", notes = "카테고리 등록")
    @PostMapping("/category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult saveCategory(@ApiParam(value = "카테고리 이름", required = true) @RequestBody CategorySaveDto categorySaveDto) throws Exception {
        categoryService.saveCategory(categorySaveDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카테고리 전체 조회", notes = "카테고리 전체 조회")
    @GetMapping("/category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public ListResult<Category> findAll() {
        return responseService.getListResult(categoryService.findAll());
    }

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리 삭제")
    @DeleteMapping("/category/{idx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult deleteByIdx(@PathVariable Long idx) {
        categoryService.deleteIdx(idx);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카테고리 수정", notes = "카테고리 수정")
    @PutMapping("/category/{idx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult updateByIdx(@PathVariable Long idx, @RequestBody CategoryUpdateDto categoryUpdateDto) {
        categoryService.updateIdx(categoryUpdateDto, idx);
        return responseService.getSuccessResult();
    }
}
