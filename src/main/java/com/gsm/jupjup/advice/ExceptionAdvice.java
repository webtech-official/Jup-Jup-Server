package com.gsm.jupjup.advice;

import com.gsm.jupjup.advice.exception.*;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.ACCEPTED)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.parseInt(getMessage("unKnown.code")), e.getMessage());
    }

    @ExceptionHandler(CUserNotFoundException.class)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("CUserNotFoundException.code")), getMessage("CUserNotFoundException.msg"));
    }

    @ExceptionHandler(CEmailSigninFailedException.class)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(CDuplicateEmailException.class)
    public CommonResult CDuplicateEmailException(HttpServletRequest request, CDuplicateEmailException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("DuplicateEmail.code")), getMessage("DuplicateEmail.msg"));
    }

    @ExceptionHandler(EquipmentNotFoundException.class)
    public CommonResult EquipmentNotFoundException(HttpServletRequest request, EquipmentNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("EquipmentNotFound.code")), getMessage("EquipmentNotFound.msg"));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public CommonResult ImageNotFoundException(HttpServletRequest request, ImageNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("ImageNotFound.code")), getMessage("ImageNotFound.msg"));
    }

    @ExceptionHandler(FileExtensionNotMatchImageException.class)
    public CommonResult FileExtensionNotMatchImageException(HttpServletRequest request, FileExtensionNotMatchImageException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("FileExtensionNotMatchImage.code")), getMessage("FileExtensionNotMatchImage.msg"));
    }

    @ExceptionHandler(EquipmentAllowAmountZeroException.class)
    public CommonResult EquipmentAllowAmountZeroException(HttpServletRequest request, EquipmentAllowAmountZeroException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("EquipmentAllowAmountZero.code")), getMessage("EquipmentAllowAmountZero.msg"));
    }

    @ExceptionHandler(EquipmentAllowAmountExceedException.class)
    public CommonResult EquipmentAllowAmountExceedException(HttpServletRequest request, EquipmentAllowAmountExceedException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("EquipmentAllowAmountExceed.code")), getMessage("EquipmentAllowAmountExceed.msg"));
    }

    @ExceptionHandler(EquipmentAllowNotFoundException.class)
    public CommonResult EquipmentAllowNotFoundException(HttpServletRequest request, EquipmentAllowNotFoundException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("EquipmentAllowNotFound.code")), getMessage("EquipmentAllowNotFound.msg"));
    }

    @ExceptionHandler(NotFoundLaptopException.class)
    public CommonResult EquipmentAllowNotFoundException(HttpServletRequest request, NotFoundLaptopException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("NotFoundLaptopException.code")), getMessage("NotFoundLaptopException.msg"));
    }

    @ExceptionHandler(NotFoundLaptopSpecException.class)
    public CommonResult EquipmentAllowNotFoundException(HttpServletRequest request, NotFoundLaptopSpecException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("NotFoundLaptopSpec.code")), getMessage("NotFoundLaptopSpec.msg"));
    }

    @ExceptionHandler(EquipmentDuplicateException.class)
    public CommonResult EquipmentAllowNotFoundException(HttpServletRequest request, EquipmentDuplicateException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("EquipmentDuplicateException.code")), getMessage("EquipmentDuplicateException.msg"));
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public CommonResult UserDoesNotExistException(HttpServletRequest request, UserDoesNotExistException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("UserDoesNotExistException.code")), getMessage("UserDoesNotExistException.msg"));
    }

    @ExceptionHandler(AlreadyApprovedAndRejectedException.class)
    public CommonResult UserDoesNotExistException(HttpServletRequest request, AlreadyApprovedAndRejectedException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("AlreadyApprovedAndRejectedException.code")), getMessage("AlreadyApprovedAndRejectedException.msg"));
    }

    @ExceptionHandler(AlreadyReturnedException.class)
    public CommonResult UserDoesNotExistException(HttpServletRequest request, AlreadyReturnedException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("AlreadyReturnedException.code")), getMessage("AlreadyReturnedException.msg"));
    }

    @ExceptionHandler(NotFoundNoticeException.class)
    public CommonResult NotFoundNoticeException(HttpServletRequest request, NotFoundNoticeException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("NotFoundNoticeException.code")), getMessage("NotFoundNoticeException.msg"));
    }

    @ExceptionHandler(ApproveApplicationFirstException.class)
    public CommonResult ApproveApplicationFirstException(HttpServletRequest request, ApproveApplicationFirstException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("ApproveApplicationFirstException.code")), getMessage("ApproveApplicationFirstException.msg"));
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public CommonResult ApproveApplicationFirstException(HttpServletRequest request, EmailNotVerifiedException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("EmailNotVerifiedException.code")), getMessage("EmailNotVerifiedException.msg"));
    }
}