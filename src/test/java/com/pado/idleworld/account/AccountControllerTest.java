package com.pado.idleworld.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pado.idleworld.domain.Agreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.amazonaws.services.ec2.model.PrincipalType.Account;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Test
    void 중복계정생성_성공() throws Exception {
        SignUpForm signUpForm1 = new SignUpForm();
        signUpForm1.setEmail("sjhsjh1990@gmail.com");
        signUpForm1.setPassword("11111111");
        signUpForm1.setNickname("일일일");
        signUpForm1.setImageUrl("skdlkasmdklasmdkl");
        signUpForm1.setAgree(true);

        mockMvc.perform(post("/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJs


                                (signUpForm1)))
                .andDo(print())
                .andExpect(status().isOk());
    }



    /*
    @Test
    void 이용약관_등록_성공() throws Exception {

        Agreement.Request agreementRequestDto = new Agreement.Request();
        agreementRequestDto.setTitle("약관 제목");
        agreementRequestDto.setSubtitle("약관 내용");

        mockMvc.perform(post("/v1/agreement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(agreementRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 이용약관_조회_성공() throws Exception {

        Agreement agreement = new Agreement();
        agreement.setTitle("제목입니다");
        agreement.setSubtitle("내용입니다");
        agreementRepository.save(agreement);

        mockMvc.perform(get("/v1/agreement"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 이용약관_조회_실패() throws Exception {

        mockMvc.perform(get("/v1/agreement"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    @Test
    void 이용약관_수정_실패() throws Exception {
        // given
        Agreement agreement = new Agreement();
        agreement.setTitle("제목입니다");
        agreement.setSubtitle("내용입니다");
        agreementRepository.save(agreement);

        Agreement.Request agreementRequestDto = new Agreement.Request();
        agreementRequestDto.setTitle("수정된 제목입니다.");
        agreementRequestDto.setSubtitle("수정된 내용입니다.");

        // when, 존재하지 않는 항목일 때
        mockMvc.perform(put("/v1/agreement/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(agreementRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(5002));
    }

    @Test
    void 이용약관_수정_성공() throws Exception {
        // given
        Agreement agreement = new Agreement();
        agreement.setTitle("제목입니다");
        agreement.setSubtitle("내용입니다");
        agreementRepository.save(agreement);

        Agreement.Request agreementRequestDto = new Agreement.Request();
        agreementRequestDto.setTitle("수정된 제목입니다.");
        agreementRequestDto.setSubtitle("수정된 내용입니다");


        // when, 존재하지 않는 항목일 때. pathvariable 변수
        mockMvc.perform(put("/v1/agreement/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(agreementRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2020));

        String title = agreementService.findLastCreatedAgreement().getTitle();
        assertThat(title).isNotEqualTo("제목입니다.");
        assertThat(title).isEqualTo(agreementRequestDto.getTitle());
    }*/








}
