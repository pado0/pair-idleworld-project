package com.pado.idleworld.agreement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.idleworld.domain.Agreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AgreementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgreementRepository agreementRepository;

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

}