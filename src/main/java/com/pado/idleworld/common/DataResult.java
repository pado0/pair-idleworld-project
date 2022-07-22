package com.pado.idleworld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
{
    "code": 200,
    "message": "성공",
    "data" : {"~~ㄷㅔ이터"}
}
*/
@Getter
public class DataResult<T> extends CommonResult{
    private T data;

    public DataResult(ResponseCode responseCode, T data) {
        super(responseCode);
        this.data = data;
    }

}
