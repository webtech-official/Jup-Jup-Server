package com.gsm.jupjup.controller.v1;

import com.gsm.jupjup.dto.equipmentAllow.EquipmentAllowSaveDto;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.equipment.EquipmentAllowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. equipment allow"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class EquipmentAllowController {
    private final EquipmentAllowService equipmentAllowService;
    private final ResponseService responseService; // 결과를 처리할 Service

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/equipmentallow/{name}")
    public CommonResult save(@PathVariable() String name, @RequestBody EquipmentAllowSaveDto equipmentAllowSaveDto) throws Exception {
        equipmentAllowService.save(name, equipmentAllowSaveDto);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/equipmentallow/{eqa_idx}")
    public CommonResult update(@PathVariable() Long eqa_idx, @RequestBody EquipmentAllowSaveDto equipmentAllowSaveDto){
        equipmentAllowService.update(eqa_idx, equipmentAllowSaveDto);
        return responseService.getSuccessResult();
    }

}
