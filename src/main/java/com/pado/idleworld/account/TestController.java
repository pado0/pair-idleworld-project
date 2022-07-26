package com.pado.idleworld.account;

import com.pado.idleworld.account.mail.MailDTO;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final AccountService accountService;

    @GetMapping("/join")
    public String join() {
        return "account/join";
    }

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

//    @GetMapping("/loginProc")
//    public @ResponseBody String main() {
//        return "로그인 완료!!";
//    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "Admin Page";
    }

    @Secured("ROLE_ADMIN")  //ROLE_ADMIN 권한자만 접근 가능
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    //@PostAuthorize() //이건 잘 안씀
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")  //data메서드 실행되기 직전에 실행, 권한 다수일 경우 사용
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }


    @GetMapping("/change-password")
    public String changePassword() {
        return "account/change-password";
    }


}
