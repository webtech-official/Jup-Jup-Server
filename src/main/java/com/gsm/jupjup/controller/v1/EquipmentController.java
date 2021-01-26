package com.gsm.jupjup.controller.v1;

import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.service.admin.AdminService;
import com.gsm.jupjup.service.equipment.EquipmentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"2. 관리자"})
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final AdminService adminService;
    private final ResponseService responseService; // 결과를 처리할 Service

    @ApiOperation(value = "기자제 조회", notes = "기자제를 조회한다.")
    @GetMapping("/equipment/{eq_Idx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public SingleResult<EquipmentResDto> findByName(
            @ApiParam(value = "기자재 Idx", required = true) @PathVariable Long eq_Idx) throws Exception {
        return responseService.getSingleResult(equipmentService.findByIdx(eq_Idx));
    }

    @ApiOperation(value = "기자제 등록", notes = "기자제를 등록한다.")
    @PostMapping("/equipment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult save(@ApiParam(value = "기자재 이미지", required = true) @RequestParam("img_equipment") MultipartFile img_equipment,
                             @ApiParam(value = "기자재 이름", required = true) @RequestParam String name,
                             @ApiParam(value = "기자재 유형", required = true) @RequestParam String content,
                             @ApiParam(value = "기자재 개수", required = true) @RequestParam int count) throws Exception {
        EquipmentUploadDto equipmentUploadDto
                = EquipmentUploadDto.builder()
                .img_equipment(img_equipment)
                .name(name)
                .content(content)
                .count(count)
                .build();
        equipmentService.save(equipmentUploadDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "기자제 수정", notes = "기자제를 인덱스를 기준으로 이름을 수정한다.")
    @PutMapping("/equipment/{eq_Idx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult update(@ApiParam(value = "기자재 Idx", required = true) @PathVariable Long eq_Idx,
                       @ApiParam(value = "기자재 이", required = true) String name) throws Exception {
        equipmentService.update(eq_Idx, name);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "기자제 삭제", notes = "기자제를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/equipmnet/{eq_Idx}")
    public CommonResult deleteByIdx(@ApiParam(value = "기자재 Idx", required = true) @PathVariable Long eq_Idx) throws Exception {
        equipmentService.deleteByIdx(eq_Idx);
        return responseService.getSuccessResult();
    };

    @ApiOperation(value = "신청 전체 조회", notes = "신청을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/applyview")
    public List<EquipmentAllow> findAll(){
        return adminService.findAllFetch();
    }
}