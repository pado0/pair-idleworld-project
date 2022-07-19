package com.pado.idleworld.account;

import com.pado.idleworld.domain.Account;
import com.pado.idleworld.domain.AccountRole;
import com.pado.idleworld.exception.LoginInfoMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginIdPwValidator implements UserDetailsService {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String insertedEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(insertedEmail);

        if (account == null) {
            throw new LoginInfoMismatchException();
        }

        String pw = account.getPassword();
        AccountRole roles = account.getRole();

        return User.builder()
                .username(insertedEmail)
                .password(pw)
                .roles(String.valueOf(roles))
                .build();
    }
}
