package com.gsm.jupjup.controller.v1;


import com.gsm.jupjup.dto.laptop.LaptopSaveRequestDto;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.mypage.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"5. 마이페이지"})
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MyPageController {

    final MyPageService myPageService;
    final ResponseService responseService;

    //GET
    @ApiOperation(value = "회원 기자재 조회", notes = "내가 빌린 기자재를 조회한다.")
    @GetMapping("/myequipment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public ListResult<EquipmentAllow> findMyEqiupment(HttpServletRequest req){
        return responseService.getListResult(myPageService.findMyEquipment(req));
    }

    //GET
    @ApiOperation(value = "회원 노트북 조회", notes = "나의 노트북을 조회한다.")
    @GetMapping("/mylaptop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public ListResult<Laptop> findMyLaptop(HttpServletRequest req){
        return responseService.getListResult(myPageService.findMyLaptop(req));
    }

}
