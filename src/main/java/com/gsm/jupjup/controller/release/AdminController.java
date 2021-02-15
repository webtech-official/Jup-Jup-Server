package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.config.handler.NotFoundImageHandler;
import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.response.*;
import com.gsm.jupjup.service.admin.AdminService;
import com.gsm.jupjup.service.equipment.EquipmentAllowService;
import com.gsm.jupjup.service.equipment.EquipmentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = {"2. 관리자"})
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:3000") //해당 origin 승인하기
@RequiredArgsConstructor
public class AdminController {
    private final EquipmentService equipmentService;
    private final EquipmentAllowService equipmentAllowService;
    private final AdminService adminService;
    private final ResponseService responseService; // 결과를 처리할 Service

    @ApiOperation(value = "기자재 조회", notes = "기자재를 조회한다.")
    @GetMapping("/equipment/{name}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public SingleResult<EquipmentResDto> EquipmentFindByName(
            @ApiParam(value = "기자재 이름", required = true) @PathVariable String name) throws Exception {
        return responseService.getSingleResult(equipmentService.findByName(name));
    }

    @ApiOperation(value = "기자재 등록", notes = "기자재를 등록한다.")
    @PostMapping(path = "/admin/equipment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult save(@ApiParam(value = "기자재 이미지", required = false) @RequestParam(value = "img_equipment",required = false) MultipartFile img_equipment,
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
        //기자재 등록 중복 처리

        equipmentService.save(equipmentUploadDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "기자재 전체 조회", notes = "기자재를 천체 조회한다.")
    @GetMapping("/equipment/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public ListResult<EquipmentResDto> equipmentFindAll() throws IOException {
        return responseService.getListResult(equipmentService.findAll());
    }

    @ApiOperation(value = "기자재 수량 변경", notes = "기자재 수량을 변경한다.")
    @PutMapping("/admin/equipment/{name}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult update(@ApiParam(value = "기자재 이름", required = true) @PathVariable String name,
                               @ApiParam(value = "변경 기자재 수량", required = true) @RequestParam int count) throws Exception {
        equipmentService.update(name, count);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "기자재 전체 수정", notes = "기자재를 수정한다.")
    @PutMapping("/admin/equipmentAll/{oldName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult update( @ApiParam(value = "수정할 기자재 이름", required = true) @PathVariable String oldName,
                                @ApiParam(value = "기자재 이미지", required = false) @RequestParam(value = "img_equipment",required = false) MultipartFile img_equipment,
                                @ApiParam(value = "기자재 이름", required = true) @RequestParam String newName,
                                @ApiParam(value = "기자재 유형", required = true) @RequestParam String content,
                                @ApiParam(value = "기자재 개수", required = true) @RequestParam int count) throws Exception, NotFoundImageHandler {
        equipmentService.AllUpdate(
                oldName,
                new EquipmentUploadDto().builder()
                        .name(newName)
                        .content(content)
                        .count(count)
                        .img_equipment(img_equipment)
                        .build()
        );
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "기자재 삭제", notes = "기자재를 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/admin/equipmnet/delete/{equipmentidx}")
    public CommonResult deleteByIdx(@ApiParam(value = "기자재 Idx", required = true) @PathVariable Long equipmentidx) throws Exception {
        equipmentService.deleteByEquipmentIdx(equipmentidx);
        return responseService.getSuccessResult();
    };

    @ApiOperation(value = "신청 전체 조회", notes = "신청을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/admin/applyview")
    public ListResult<Object> findAll(){
        List<Object> equipmentAllowListResult = adminService.findAll();
        return responseService.getListResult(equipmentAllowListResult);
    }

    //신청 승인
    @ApiOperation(value = "신청 승인", notes = "관리자가 신청을 승인한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/admin/approved/{eqa_Idx}")
    public CommonResult ApprovedAllow(@ApiParam(value = "신청 Idx", required = true) @PathVariable Long eqa_Idx){
        equipmentAllowService.SuccessAllow(eqa_Idx);
        return responseService.getSuccessResult();
    }

    //신청 거절 + 숫자 더하기
    @ApiOperation(value = "신청 거절", notes = "관리자가 신청을 거절한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/admin/reject/{eqa_Idx}")
    public CommonResult RejectAllow(@ApiParam(value = "신청 Idx", required = true) @PathVariable Long eqa_Idx){
        equipmentAllowService.FailureAllow(eqa_Idx);
        return responseService.getSuccessResult();
    }

    //반납 + 숫자 더하기
    @ApiOperation(value = "반납", notes = "관리자가 반납을 처리한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/admin/return/{eqa_Idx}")
    public CommonResult ReturnAllow(@ApiParam(value = "신청 Idx", required = true) @PathVariable Long eqa_Idx){
        equipmentAllowService.ReturnAllow(eqa_Idx);
        return responseService.getSuccessResult();
    }

    //기자재 키워드(이름) 검색
    @ApiOperation(value = "키워드 검색", notes = "유저가 키원드를 사용하여 기자재를 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/equipment/{keyword}")
    public ListResult<Equipment> findByKeyWord(@ApiParam(value = "검색 KeyWord", required = true) @PathVariable String keyword) throws Exception {
        return responseService.getListResult(equipmentService.findByKeyword(keyword));
    }
}
