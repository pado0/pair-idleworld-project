package com.pado.idleworld.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
{
    "code": 200,
    "message": "성공"
}
*/
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


