package com.pado.idleworld.security;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
public class AccountLoginFailureHandler implements AuthenticationFailureHandler {

    private final AccountRepository accountRepository;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            String email = request.getParameter("email");

            Account findAccount = accountRepository.findByEmail(email);
            if (findAccount == null) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }

            findAccount.addFailCount();

            if (findAccount.getFailCount() >= 3) {
                request.setAttribute("isLocked", true);
            } else {
                request.setAttribute("isFailed", true);
            }
        } else  if (exception instanceof LockedException) {
            request.setAttribute("isLocked", true);

        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
        requestDispatcher.forward(request, response);

    }
}
