package com.pado.idleworld.account;

import com.pado.idleworld.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.LongConsumer;

//@Repository
public interface AccountRepository extends JpaRepository<Account, LongConsumer> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Account findByEmail(String email);
}
