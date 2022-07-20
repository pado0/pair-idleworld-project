package com.pado.idleworld;

import com.pado.idleworld.account.AccountService;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.domain.AccountRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import static com.pado.idleworld.domain.AccountRole.ROLE_ADMIN;


@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {


        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        private final EntityManager em;

        public void dbInit1() {
            Account account = createAccount("master@gmail.com", "11111111", ROLE_ADMIN);
            em.persist(account);
        }



        private Account createAccount(String email, String password, AccountRole role) {
            String rawPw = password;
            String encPw = bCryptPasswordEncoder.encode(rawPw);
            Account account = new Account();
            account.setEmail(email);
            account.setPassword(encPw);
            account.setRole(role);
            return account;
        }
    }
}
