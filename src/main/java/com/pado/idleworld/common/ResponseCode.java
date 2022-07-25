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
    NO_SUCH_ELEMENT_EXISTS(5002, "존재하지 않는 항목입니다"),
    DUPLICATION_ELEMENT_EXISTS(5004, "이메일 중복입니다."),
    LOGIN_INFO_MISMATCH(5005,"아이디 혹은 패스워드가 일치하지 않습니다."),
    ACCOUNT_LOCKED_BY_LOGIN_FAIL(5006, "로그인 실패로 계정이 잠겼습니다. 비밀번호 재설정을 해주세요.");


    private int code;
    private String message;

}
