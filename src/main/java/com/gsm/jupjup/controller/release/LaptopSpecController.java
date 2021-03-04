package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecDto;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.service.response.ResponseService;
import com.gsm.jupjup.service.laptop.LaptopSpecService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Api(tags = "7. 노트북 사양")
@RestController
@CrossOrigin(origins = "http://localhost:3000") //해당 origin 승인하기
@RequestMapping("/v2/admin")
public class LaptopSpecController {

    private final LaptopSpecService laptopSpecService;
    private final ResponseService responseService;

    @ApiOperation(value = "노트북 스팩 저장", notes = "노트북의 스팩을 저장한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/laptop-spec")
    public CommonResult save(@ApiParam(value = "노트북 스펙 정보", required = true) @RequestBody LaptopSpecDto laptopSpecDto){
        laptopSpecService.save(laptopSpecDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "노트북 스팩 모두 조회", notes = "노트북의 스팩을 모두 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/laptop-spec")
    public ListResult<LaptopSpec> findALl(){
        return responseService.getListResult(laptopSpecService.findAll());
    }

    @ApiOperation(value = "노트북 스팩 조회", notes = "노트북의 스팩을 Idx를 통해 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/laptop-spec/{specIdx}")
    public CommonResult findByIdx(@ApiParam(value = "노트북 스펙 Idx", required = true) @PathVariable Long specIdx){
        return responseService.getSingleResult(laptopSpecService.findBySpecIdx(specIdx));
    }

    @ApiOperation(value = "노트북 스팩 수정", notes = "노트북의 스팩을 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/laptop-spec/{specIdx}")
    public CommonResult update(@ApiParam(value = "노트북 스펙 Idx", required = true) @PathVariable Long specIdx,
                               @ApiParam(value = "New 노트북 스펙 정보", required = true) @RequestBody LaptopSpecDto laptopSpecDto){
        laptopSpecService.updateBySpecIdx(specIdx, laptopSpecDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "노트북 스팩 삭제", notes = "노트북의 스팩을 삭제한다.(스팩을 삭제하면 관련된 노트북들도 함께 삭제된다.)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/laptop-spec/{specIdx}")
    public CommonResult deleteBySpecIdx(@ApiParam(value = "노트북 스펙 Idx", required = true) @PathVariable Long specIdx){
        laptopSpecService.deleteBySpecIdx(specIdx);
        return responseService.getSuccessResult();
    }
}
