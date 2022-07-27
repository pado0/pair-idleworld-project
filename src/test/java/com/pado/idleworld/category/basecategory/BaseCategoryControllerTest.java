package com.pado.idleworld.category.basecategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.idleworld.category.midcategory.MidCategoryCreateRequest;
import com.pado.idleworld.category.midcategory.MidCategoryRepository;
import com.pado.idleworld.category.topcategory.TopCategoryCreateRequest;
import com.pado.idleworld.category.topcategory.TopCategoryRepository;
import com.pado.idleworld.domain.Agreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureRestDocs(outputDir = "target/snippets")
class BaseCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MidCategoryRepository midCategoryRepository;

    @Autowired
    private TopCategoryRepository topCategoryRepository;

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void 베이스_카테고리_등록() throws Exception {

        TopCategoryCreateRequest topCategoryCreateRequest = new TopCategoryCreateRequest();
        topCategoryCreateRequest.setTitle("최상위 카테고리");
        topCategoryCreateRequest.setImageUrl("top.com");

        // id 조회해서 넣기
        MidCategoryCreateRequest midCategoryCreateRequest = new MidCategoryCreateRequest();
        // midCategoryCreateRequest.setTopCategoryId();

        // given
        BaseCategoryCreateRequest baseCategoryCreateRequest = new BaseCategoryCreateRequest();
        // baseCategoryCreateRequest.setMidCategoryId(1);

        Agreement.Request agreementRequestDto = new Agreement.Request();
        agreementRequestDto.setTitle("약관 제목");
        agreementRequestDto.setSubtitle("약관 내용");

        mockMvc.perform(post("/v1/agreement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(agreementRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("agreement",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("title"),
                                fieldWithPath("subtitle").description("subtitle")

                        ),
                        responseFields(
                                fieldWithPath("code").description("code"),
                                fieldWithPath("message").description("message")
                        ))
                );
    }


}