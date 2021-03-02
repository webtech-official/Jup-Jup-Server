package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.dto.equipmentAllow.EquipmentAllowSaveDto;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.equipment.EquipmentAllowService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"3. 학생"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") //해당 origin 승인하기
@RequestMapping("/v2")
public class StudentController {
    private final EquipmentAllowService equipmentAllowService;
    private final ResponseService responseService; // 결과를 처리할 Service

    @ApiOperation(value = "기자재 신청", notes = "기자재를 신청한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/equipmentallow/{name}")
    public CommonResult save(@ApiParam(value = "기자재 신청 이름", required = true) @PathVariable() String name,
                             @ApiParam(value = "기자재 신청 Dto", required = true) @RequestBody EquipmentAllowSaveDto equipmentAllowSaveDto) {
        equipmentAllowService.save(name, equipmentAllowSaveDto);
        return responseService.getSuccessResult();
    }

//    @ApiOperation(value = "기자제 신청 수정", notes = "기자재 신청 내용을 수정한다.")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @PutMapping("/equipmentallow/{eqa_idx}")
//    public CommonResult update(@ApiParam(value = "기자재 신청 인덱스", required = true) @PathVariable() Long eqa_idx,
//                               @ApiParam(value = "기자재 신청 Dto", required = true) @RequestBody EquipmentAllowSaveDto equipmentAllowSaveDto){
//        equipmentAllowService.update(eqa_idx, equipmentAllowSaveDto);
//        return responseService.getSuccessResult();
//    }
//
//    @ApiOperation(value = "기자제재 신청 삭제", notes = "기자재 신청를 삭제한다.")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @DeleteMapping("/equipmentallow/{eqa_idx}")
//    public CommonResult update(@ApiParam(value = "기자재 신청 인덱스", required = true)@PathVariable Long eqa_idx){
//        equipmentAllowService.deleteById(eqa_idx);
//        return responseService.getSuccessResult();
//    }
}
