package com.gsm.jupjup.controller.v1;

import com.gsm.jupjup.dto.laptop.LaptopResponseDto;
import com.gsm.jupjup.dto.laptop.LaptopSaveRequestDto;
import com.gsm.jupjup.service.laptop.LaptopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. 노트북"})
@RestController
@RequestMapping("/v1")
public class LaptopController {
    //Dependency Injection
    @Autowired
    private LaptopService laptopService;
    //CREATE
    @PostMapping("/laptop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public String save(@RequestBody LaptopSaveRequestDto laptopSaveRequestDto){
        return laptopService.save(laptopSaveRequestDto);
    }
    //READ
    @GetMapping("/laptop/{laptopSerialNumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public LaptopResponseDto findByLaptopSerialNumber(@PathVariable String laptopSerialNumber){
        return laptopService.findByLaptopSerialNumber(laptopSerialNumber);
    }
    //UPDATE
    @PutMapping("/laptop/{laptopSerialNumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public String update(@PathVariable String laptopSerialNumber, @RequestBody LaptopSaveRequestDto laptopSaveRequestDto){
        return laptopService.update(laptopSerialNumber, laptopSaveRequestDto);
    }
    //DELETE
    @DeleteMapping("/laptop/{laptopSerialNumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public String delete(@PathVariable String laptopSerialNumber){
        laptopService.delete(laptopSerialNumber);
        return laptopSerialNumber;
    }
}
