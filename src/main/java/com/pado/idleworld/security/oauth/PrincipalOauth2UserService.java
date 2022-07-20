package com.pado.idleworld.security.oauth;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.account.AccountService;
import com.pado.idleworld.account.SignUpForm;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private final AccountService accountService;

    private final AccountRepository accountRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리를 하는 함수
    //메서드 종료시 @AuthenticationPrincipal 어노테이션 생성
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();    //google
        String providerId = oAuth2User.getAttribute("sub");
        String nickname = provider+"_"+providerId;

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setEmail(oAuth2User.getAttribute("email"));
        signUpForm.setPassword("겟인데어");
        signUpForm.setProvider(provider);
        signUpForm.setProviderId(providerId);
        signUpForm.setNickname(nickname);

        Account accountEntity = accountRepository.findByEmail(signUpForm.getEmail());

        if (accountEntity == null) {

            accountService.accountCreate(signUpForm);

        }


        return new PrincipalDetails(accountEntity, oAuth2User.getAttributes());
    }
}
