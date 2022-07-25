package com.pado.idleworld.security;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.exception.LoginInfoMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
public class AccountLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final AccountRepository accountRepository;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        if (exception instanceof InternalAuthenticationServiceException) {  //시스템 문제로 내부 인증 관련 처리 못하는 경우(ID 없는 경우에도 발생
            throw new LoginInfoMismatchException();
        } else if (exception instanceof BadCredentialsException) { //비밀번호 불일치
            String email = request.getParameter("email");

            Account findAccount = accountRepository.findByEmail(email);

            findAccount.addFailCount();

            if (findAccount.getFailCount() >= 3) {
                request.setAttribute("isLocked", true);
            } else {
                request.setAttribute("isFailed", true);
            }
        } else if (exception instanceof LockedException) { //인증 거부 : 잠긴 계정
            request.setAttribute("isLocked", true);
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {   //인증 요구 거부된 경우

        }else if (exception instanceof DisabledException) { //인증 거부 : 계정 비활성화

        }else if (exception instanceof AccountExpiredException) {   //인증 거부 : 계정 유효기간 만료

        }else if (exception instanceof CredentialsExpiredException) {   //인증 거부 : 비밀번호 유효 기간 만료

        }




        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
        requestDispatcher.forward(request, response);

    }



}
