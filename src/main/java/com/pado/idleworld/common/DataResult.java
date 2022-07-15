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

/* 사용법 */
/*
// 아래 코드와 같이 DataResult<응답클래스> 형태로 한 번 감싸서 리턴
// todo는 적용 필요하지만 일단 아래와 같이 개발하는걸로 !

    @PostMapping("/api/sign-up")
    public DataResult<Account> signUp(@Valid @RequestBody SignUpForm signUpForm){


        // todo : 비즈니스 로직에 의한 예외사항 발생, 이 로직 서비스 레이어로 내리기. Exception은 글로벌에서 처리
        if(accountRepository.existsByEmail(signUpForm.getEmail()))
            throw new AccountCannotCreateException(ExceptionCode.MEMBER_DUPLICATE);

        Account account = accountService.createAccount(signUpForm);

        // todo : DataResult 부분 생성자로 리팩토링하기
        DataResult<Account> result = DataResult.<Account>builder()
                .status(HttpStatus.OK)
                .message("정상응답")
                .data(account).build();

        return result;
    }

* */