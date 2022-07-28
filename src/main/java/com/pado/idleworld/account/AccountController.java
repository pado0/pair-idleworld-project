package com.pado.idleworld.account;

import com.pado.idleworld.account.mail.MailDTO;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.exception.LoginInfoMismatchException;
import com.pado.idleworld.security.PrincipalDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Lob;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;


    //회원가입
    @PostMapping("/sign-up")
    public CommonResult accountSignUp(@RequestBody @Valid SignUpForm request) {
        accountService.accountCreate(request);

        return new CommonResult(ResponseCode.SUCCESS);
    }

    /*
    시큐리티 로그인 기능으로 삭제
    @GetMapping("/account/login")
    public CommonResult accountLogin(@RequestBody @Valid LoginForm loginForm) {

        if (!accountRepository.existsByEmail(loginForm.getEmail())) {
            throw new LoginInfoMismatchException();
        }
        Account findAccount = accountRepository.findByEmail(loginForm.getEmail());
        if (!loginForm.getPassword().equals(findAccount.getPassword())) {
            throw new LoginInfoMismatchException();
        }
        return new CommonResult(ResponseCode.SUCCESS);
    }*/

    //todo : playList 가져오면 널포인트

    //정보 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/account/{accountEmail}")
    public DataResult accountInfo(@PathVariable("accountEmail") String email) {

        return new DataResult(ResponseCode.SUCCESS, accountService.accountRead(email));
    }

    //정보 조회(로그인 세션 기준)
    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/account")
    public DataResult accountInfo() {
        String email = accountService.nowSessionEmail();

        return new DataResult(ResponseCode.SUCCESS, accountService.accountRead(email));
    }

    //계정 업데이트
    @Secured("ROLE_ADMIN")
    @PutMapping("/account/{accountEmail}")
    public CommonResult accountUpdate(@PathVariable("accountEmail") String email,@RequestBody AccountUpdateRequest request) {
        accountService.accountUpdate(email, request);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    //계정 업데이트(로그인 세션 기준)
    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PutMapping("/account")
    public CommonResult accountUpdate(@RequestBody AccountUpdateRequest request) {
        String email = accountService.nowSessionEmail();
        accountService.accountUpdate(email, request);
        return new CommonResult(ResponseCode.SUCCESS);
    }


    //임시 패스워드 발급
    @PostMapping("/sendEmail")
    public CommonResult sendEmail(@RequestParam("memberEmail") String memberEmail) {
        MailDTO dto = accountService.createMailAndChangePassword(memberEmail);
        accountService.mailSend(dto);

        return new CommonResult(ResponseCode.SUCCESS); //todo 응답으로 바꿔주자
    }

    //계정 권한 admin 변경
    @Secured("ROLE_ADMIN")
    @PostMapping("/account/role/admin")
    public CommonResult roleAdmin(@RequestParam("memberEmail") String memberEmail) {
        accountService.roleAdmin(memberEmail);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    //계정 권한 user 변경
    @Secured("ROLE_ADMIN")
    @PostMapping("/account/role/user")
    public CommonResult roleUser(@RequestParam("memberEmail") String memberEmail) {
        accountService.roleUser(memberEmail);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    //로그인 성공
    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/loginProc")
    public CommonResult loginSuccess() {
        return new CommonResult(ResponseCode.SUCCESS);
    }



/*
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
*/
}
