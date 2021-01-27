package com.gsm.jupjup.controller.v1;

import com.gsm.jupjup.dto.laptop.LaptopResponseDto;
import com.gsm.jupjup.dto.laptop.LaptopSaveRequestDto;
import com.gsm.jupjup.dto.laptop.LaptopUpdateRequestDto;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.service.laptop.LaptopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Api(tags = {"4. 노트북"})
@RestController
@RequestMapping("/v1")
public class LaptopController {
    //Dependency Injection
    private final LaptopService laptopService;
    private final ResponseService responseService;

    //CREATE
    @PostMapping("/laptop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult save(@RequestBody LaptopSaveRequestDto laptopSaveRequestDto,
                             HttpServletRequest req){
        laptopService.save(laptopSaveRequestDto, req);
        return responseService.getSuccessResult();
    }
    //READ
    @GetMapping("/laptop/{laptopSerialNumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public SingleResult<LaptopResponseDto> findByLaptopSerialNumber(@PathVariable String laptopSerialNumber){
        return responseService.getSingleResult(laptopService.findByLaptopSerialNumber(laptopSerialNumber));
    }
    //UPDATE
    @PutMapping("/laptop/{laptopSerialNumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult update(@PathVariable String laptopSerialNumber, @RequestBody LaptopUpdateRequestDto laptopUpdateRequestDto){
        laptopService.update(laptopSerialNumber, laptopUpdateRequestDto);
        return responseService.getSuccessResult();
    }
    //DELETE
    @DeleteMapping("/laptop/{laptopSerialNumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult delete(@PathVariable String laptopSerialNumber){
        laptopService.delete(laptopSerialNumber);
        return responseService.getSuccessResult();
    }
}
