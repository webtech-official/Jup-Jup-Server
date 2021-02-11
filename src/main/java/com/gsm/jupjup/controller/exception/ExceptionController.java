package com.gsm.jupjup.controller.exception;

import com.gsm.jupjup.advice.exception.*;
import com.gsm.jupjup.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() {
        throw new AccessDeniedException("");
    }

    @GetMapping(value = "/duplicateemail")
    public CommonResult duplicateemailException() {
        throw new CDuplicateEmailException();
    }

    @GetMapping(value = "/notfoundequipment")
    public CommonResult NotFoundEquipmentException() {
        throw new EquipmentNotFoundException();
    }

    @GetMapping(value = "/imagenotfound")
    public CommonResult ImageNotFoundException() {throw new ImageNotFoundException("");}

    @GetMapping(value = "/equipmentallowamountzero")
    public CommonResult EquipmentAllowAmountZeroException() {throw new EquipmentAllowAmountZeroException("");}

    @GetMapping(value = "/equipment-allow-amount-exceed")
    public CommonResult EquipmentAllowAmountExceedException() {throw new EquipmentAllowAmountExceedException("");}

    @GetMapping(value = "/equipment-allow-not-found-exceed")
    public CommonResult EquipmentAllowNotFoundException() {throw new EquipmentAllowNotFoundException("");}

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
}