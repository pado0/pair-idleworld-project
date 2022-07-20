package com.pado.idleworld.security;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/v1/account/login")
//로그인 요청이 요면 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {


    private final AccountRepository accountRepository;


    //함수 종료시 @AuthenticationPrincipal 어노테이션 생성
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account != null) {
            return new PrincipalDetails(account);
            //이게 시큐리티 세션 내부의 Authentication로 리턴됨
        }
        return null;
    }
}
