package com.pado.idleworld.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ResponseCode {

    SUCCESS(200, "성공");

    @Getter
    private int code;

    @Getter
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
