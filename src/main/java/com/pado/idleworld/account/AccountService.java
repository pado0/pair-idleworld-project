package com.pado.idleworld.account;

import com.pado.idleworld.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void accountUpdate(String email, AccountUpdateRequest request) {
        Account findAccount = accountRepository.findByEmail(email);
        findAccount.setPassword(request.getPassword());
        findAccount.setImageUrl(request.getImageUrl());
        findAccount.setNickname(request.getNickname());
    }

}
