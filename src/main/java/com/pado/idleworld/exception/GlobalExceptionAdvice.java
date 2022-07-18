package com.pado.idleworld.exception;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleAgreementNotExistsException(AgreementNotExistsException e){
        return new CommonResult(ResponseCode.AGREEMENT_NOT_EXISTS);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleNoSuchElementException(NoSuchElementException e){
        return new CommonResult(ResponseCode.NO_SUCH_ELEMENT_EXISTS);
    }

    //todo : 에러메시지, 응답코드 확인
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleDuplicationElementException(DuplicationElementException e) {
        return new CommonResult(ResponseCode.DUPLICATION_ELEMENT_EXISTS);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleLoginInfoMismatchException(LoginInfoMismatchException e) {
        return new CommonResult(ResponseCode.LOGIN_INFO_MISMATCH);
    }

}
