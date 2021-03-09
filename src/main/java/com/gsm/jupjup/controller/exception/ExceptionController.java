package com.gsm.jupjup.controller.exception;

import com.gsm.jupjup.advice.exception.*;
import com.gsm.jupjup.model.response.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @ApiOperation(value = "로그인 오류", notes = "사용자가 로그인을 실패할때 발생하는 오류")
    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @ApiOperation(value = "접근 오류", notes = "사용자가 관리자 리소스에 접근 했을때 발생하는 오류")
    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() {
        throw new AccessDeniedException("");
    }

    @ApiOperation(value = "이메일 중복 오류", notes = "회원가입 시 이메일이 중복되면 발생하는 오류")
    @GetMapping(value = "/duplicateemail")
    public CommonResult duplicateemailException() {
        throw new CDuplicateEmailException();
    }

    @ApiOperation(value = "기자재 오류", notes = "검색하는 기자재가 없을때 발생하는 오류")
    @GetMapping(value = "/notfoundequipment")
    public CommonResult NotFoundEquipmentException() {
        throw new EquipmentNotFoundException();
    }

    @ApiOperation(value = "이미지 오류", notes = "이미지가 없을때 발생하는 오류")
    @GetMapping(value = "/imagenotfound")
    public CommonResult ImageNotFoundException() {throw new ImageNotFoundException("");}

    @ApiOperation(value = "기자재 신청 오류", notes = "사용자가 기자재를 신청할때 신청 갯수가 0일때 발생하는 오류")
    @GetMapping(value = "/equipmentallowamountzero")
    public CommonResult EquipmentAllowAmountZeroException() {throw new EquipmentAllowAmountZeroException("");}

    @ApiOperation(value = "", notes = "")
    @GetMapping(value = "/equipment-allow-amount-exceed")
    public CommonResult EquipmentAllowAmountExceedException() {throw new EquipmentAllowAmountExceedException("");}

    @ApiOperation(value = "기자재 신청 오류", notes = "사용자가 기자재를 신청할때 신청 갯수가 0일때 발생하는 오류")
    @GetMapping(value = "/equipment-allow-not-found-exceed")
    public CommonResult EquipmentAllowNotFoundException() {throw new EquipmentAllowNotFoundException("");}

    @ApiOperation(value = "기자재 신청 오류", notes = "사용자가 기자재를 신청할때 신청 갯수가 0일때 발생하는 오류")
    @GetMapping(value = "/notfoundlaptop")
    public CommonResult NotFoundLaptop() {throw new NotFoundLaptopException("");}

    @GetMapping(value = "/notfoundlaptopspec")
    public CommonResult NotFoundLaptopSpec() {throw new NotFoundLaptopException("");}

    @GetMapping(value = "/exception/file-extension-not-match-image")
    public CommonResult FileExtensionNotMatchImageException() {throw new FileExtensionNotMatchImageException();}

    @GetMapping(value = "/exception/equipmentduplicate")
    public CommonResult EquipmentDuplicateException() {throw new EquipmentDuplicateException();}

    @GetMapping(value = "/exception/alreadyapprovedAndrejected")
    public CommonResult AlreadyApprovedAndRejectedException() {throw new AlreadyApprovedAndRejectedException();}

    @GetMapping(value = "/exception/alreadyreturned")
    public CommonResult AlreadyReturnedException() {throw new AlreadyReturnedException();}

    @GetMapping(value = "/exception/NotFoundNoticeException")
    public CommonResult NotFoundNoticeException() {throw new NotFoundNoticeException();}

    @GetMapping(value = "/exception/ApproveApplicationFirst")
    public CommonResult ApproveApplicationFirstException() {throw new ApproveApplicationFirstException();}

    @GetMapping(value = "/exception/EmailNotVerifiedException")
    public CommonResult EmailNotVerifiedException() {throw new EmailNotVerifiedException();}
}