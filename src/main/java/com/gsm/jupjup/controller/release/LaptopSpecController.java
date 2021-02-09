package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecDto;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.laptop.LaptopSpecService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Api(tags = "노트북 사양")
@RestController
@RequestMapping("/v1")
public class LaptopSpecController {

    private final LaptopSpecService laptopSpecService;
    private final ResponseService responseService;

    @PostMapping("/laptop-spec")
    public CommonResult save(@RequestBody LaptopSpecDto laptopSpecDto){
        laptopSpecService.save(laptopSpecDto);
        return responseService.getSuccessResult();
    }

    @GetMapping("/laptop-spec")
    public ListResult<LaptopSpec> findALl(){
        return responseService.getListResult(laptopSpecService.findAll());
    }

    @GetMapping("/laptop-spec/{specIdx}")
    public CommonResult findByIdx(@PathVariable Long specIdx){
        return responseService.getSingleResult(laptopSpecService.findBySpecIdx(specIdx));
    }

    @PutMapping("/laptop-spec/{specIdx}")
    public CommonResult update(@PathVariable Long specIdx, @RequestBody LaptopSpecDto laptopSpecDto){
        laptopSpecService.updateBySpecIdx(specIdx, laptopSpecDto);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/laptop-spec/{specIdx}")
    public CommonResult deleteBySpecIdx(@PathVariable Long specIdx){
        laptopSpecService.deleteBySpecIdx(specIdx);
        return responseService.getSuccessResult();
    }
}
