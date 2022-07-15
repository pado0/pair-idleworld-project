package com.pado.idleworld.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Result<T> {

    private HttpStatus status;
    private String message;

    private T data;

    public Result(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

/* 사용법 */
/*
// 아래 코드와 같이 Result<응답클래스> 형태로 한 번 감싸서 리턴
// todo는 적용 필요하지만 일단 아래와 같이 개발하는걸로 !

    @PostMapping("/api/sign-up")
    public Result<Account> signUp(@Valid @RequestBody SignUpForm signUpForm){


        // todo : 비즈니스 로직에 의한 예외사항 발생, 이 로직 서비스 레이어로 내리기. Exception은 글로벌에서 처리
        if(accountRepository.existsByEmail(signUpForm.getEmail()))
            throw new AccountCannotCreateException(ExceptionCode.MEMBER_DUPLICATE);

        Account account = accountService.createAccount(signUpForm);

        // todo : Result 부분 생성자로 리팩토링하기
        Result<Account> result = Result.<Account>builder()
                .status(HttpStatus.OK)
                .message("정상응답")
                .data(account).build();

        return result;
    }

* */