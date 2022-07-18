package com.pado.idleworld.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(2020, "성공"),
    FAIL(4000, "실패"),
    AGREEMENT_NOT_EXISTS(5001, "등록된 약관이 없습니다"),
    NO_SUCH_ELEMENT_EXISTS(5002, "존재하지 않는 항목입니다");

    private int code;
    private String message;

}
