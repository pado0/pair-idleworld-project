package com.pado.idleworld.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 오류응답과 성공응답을 본 클래스에서 처리
// 코드는 ResponseCode 에서 관리
@Getter
public class CommonResult <T>{

    private int code;
    private String message;

    public CommonResult(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }
}


