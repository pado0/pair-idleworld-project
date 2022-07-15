package com.pado.idleworld.common;

import lombok.Getter;

@Getter
public class ErrorResult{
    private int status;
    private String message;

    public ErrorResult(int status, String message) {
        this.status = status;
        this.message = message;
    }
}