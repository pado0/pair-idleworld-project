package com.pado.idleworld.security.oauth;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
public class AccountLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        Account account = (Account) authentication.getPrincipal();

        Account findAccount = accountRepository.findByEmail(account.getEmail()).get();
        if (findAccount.getFailCount() >= 3) {
            throw new LockedException("계정이 잠겼습니다. 비밀번호 찾기 후 로그인 해주세요.");
        } else {
            findAccount.setFailCount();
        }
        //https://velog.io/@cjsworbehd13/Spring-Security-%EA%B3%84%EC%A0%95-%EC%A0%95%EC%A7%80-%EA%B8%B0%EB%8A%A5-1






        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }
}
