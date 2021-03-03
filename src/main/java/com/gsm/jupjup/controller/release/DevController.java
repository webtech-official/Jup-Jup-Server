package com.gsm.jupjup.controller.release;


import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.dev.DevService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Api(tags = {"dev"})
@RestController
@CrossOrigin(origins = "http://localhost:3000") //해당 origin 승인하기
@RequestMapping("/v2")
public class DevController {

    private final DevService devService;
    private final ResponseService responseService;

    @ApiOperation(value = "Admin 권한 변경", notes = "Admin으로 권한을 변경한다.")
    @PostMapping("/dev/change/admin")
    public CommonResult changeAdmin(@ApiParam(value = "Admin Idx", required = true) @PathVariable Long adminIdx) {
        devService.changeAdmin(adminIdx);
        return responseService.getSuccessResult();
    }

}
