package com.pado.idleworld.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.domain.Agreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
    @Test
    void 중복계정생성_성공() throws Exception {

        // 회원가입
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setAgree(true);
        signUpForm.setNickname("닉네임");
        signUpForm.setPassword("qwerasdf");
        signUpForm.setEmail("gywls@gmail.com");
        signUpForm.setImageUrl("asdf.com");
        //accountService.accountCreate(signUpForm);

        mockMvc.perform(post("/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(signUpForm)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void 로그인_성공() throws Exception {

        mockMvc.perform(
                        multipart("/v1/account/login")
                                .param("email", "master@gmail.com")
                                .param("password", "11111111"))
                .andExpect(redirectedUrl("/v1/loginProc"));
    }




}
