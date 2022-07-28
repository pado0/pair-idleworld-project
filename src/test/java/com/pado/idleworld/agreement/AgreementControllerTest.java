package com.pado.idleworld.agreement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.domain.AccountRole;
import com.pado.idleworld.domain.Agreement;
import com.pado.idleworld.security.PrincipalDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureRestDocs
class AgreementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private AgreementService agreementService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void 이용약관_등록_성공() throws Exception {
        // given
        Agreement.Request agreementRequestDto = new Agreement.Request();
        agreementRequestDto.setTitle("약관 제목");
        agreementRequestDto.setSubtitle("약관 내용");

        mockMvc.perform(post("/v1/agreement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(agreementRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("agreement/post", // restdocs 경로 설정
                        preprocessRequest(prettyPrint()), //json을 예쁘게 보여주기
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("title"), // json 필드의 역할을 예쁘게 표로 보여주기
                                fieldWithPath("subtitle").description("subtitle")

                        ),
                        responseFields(
                                fieldWithPath("code").description("code"),
                                fieldWithPath("message").description("message")
                        ))
                );
    }

    @Test
    @WithMockUser
    void 이용약관_조회_성공() throws Exception {

        Agreement agreement = new Agreement();
        agreement.setTitle("제목입니다");
        agreement.setSubtitle("내용입니다");
        agreementRepository.save(agreement);

        mockMvc.perform(get("/v1/agreement"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("agreement/get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").description("code"),
                                fieldWithPath("message").description("message"),
                                fieldWithPath("data.id").description("id"),
                                fieldWithPath("data.title").description("title"),
                                fieldWithPath("data.subtitle").description("subtitle")))
                );
    }


    @Test
    @WithMockUser
    void 이용약관_조회_실패() throws Exception {

        mockMvc.perform(get("/v1/agreement"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("agreement/get-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").description("code"),
                                fieldWithPath("message").description("message")
                        ))
                );

    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void 이용약관_수정_성공() throws Exception {
//        // given
//        Agreement agreement = new Agreement();
//        agreement.setTitle("제목입니다");
//        agreement.setSubtitle("내용입니다");
//        agreementRepository.save(agreement);
//
//        Agreement.Request agreementRequestDto = new Agreement.Request();
//        agreementRequestDto.setTitle("수정된 제목입니다.");
//        agreementRequestDto.setSubtitle("수정된 내용입니다");
//
//
//        // when, 존재하지 않는 항목일 때. pathvariable 변수
//        mockMvc.perform(put("/v1/agreement/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(toJson(agreementRequestDto)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(2020));
//
//        String title = agreementService.findLastCreatedAgreement().getTitle();
//        assertThat(title).isNotEqualTo("제목입니다.");
//        assertThat(title).isEqualTo(agreementRequestDto.getTitle());
//    }

}