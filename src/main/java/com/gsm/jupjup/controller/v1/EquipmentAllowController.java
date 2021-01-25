package com.gsm.jupjup.controller.v1;

import com.gsm.jupjup.dto.equipmentAllow.EquipmentAllowSaveDto;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.equipment.EquipmentAllowService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. equipment allow"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class EquipmentAllowController {
    private final EquipmentAllowService equipmentAllowService;
    private final ResponseService responseService; // 결과를 처리할 Service

    @PostMapping("/equipmentallow/{name}")
    public CommonResult saveEquipmentAllowData(@PathVariable() String name, @RequestBody EquipmentAllowSaveDto equipmentAllowSaveDto) throws Exception {
        equipmentAllowService.save(name, equipmentAllowSaveDto);
        return responseService.getSuccessResult();
    }

}
