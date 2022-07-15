package com.pado.idleworld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResult<T> extends CommonResult{
    private T data;

    public DataResult(ResponseCode responseCode) {
        super(responseCode);
    }
}
