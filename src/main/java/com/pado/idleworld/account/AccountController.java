package com.pado.idleworld.account;

import com.pado.idleworld.domain.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountRepository accountRepository;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){webDataBinder.addValidators(signUpFormValidator);}


    @GetMapping("/sign-up")
    public String accountSignUpForm(Model model) {
        model.addAttribute(new SignUpForm());

        return "account/sign-up";
    }


    @PostMapping("/sign-up")
    public String accountSignUp(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }

        // 입력 폼을 account로 전환
        Account inputAccount = Account.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .nickname(signUpForm.getNickname())
                .agree(signUpForm.isAgree())
                .build();

        // 입력 폼에 에려가 없으면 회원가입
        accountRepository.save(inputAccount);

        return "redirect:/";
    }

}
