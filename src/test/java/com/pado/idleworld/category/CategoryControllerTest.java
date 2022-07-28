package com.pado.idleworld.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.idleworld.category.basecategory.BaseCategoryCreateRequest;
import com.pado.idleworld.category.basecategory.BaseCategoryRepository;
import com.pado.idleworld.category.midcategory.MidCategoryCreateRequest;
import com.pado.idleworld.category.midcategory.MidCategoryRepository;
import com.pado.idleworld.category.topcategory.TopCategoryCreateRequest;
import com.pado.idleworld.category.topcategory.TopCategoryRepository;
import com.pado.idleworld.domain.Agreement;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.MidCategory;
import com.pado.idleworld.domain.TopCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureRestDocs
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MidCategoryRepository midCategoryRepository;

    @Autowired
    private TopCategoryRepository topCategoryRepository;

    @Autowired
    private BaseCategoryRepository baseCategoryRepository;

    @Autowired
    private EntityManager em;

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

//    @BeforeEach
//    void beforeEach(){
//
//    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void Top_카테고리_등록() throws Exception {

        // top category 만들기
        TopCategory topCategory = new TopCategory();
        topCategory.setTitle("top category");
        topCategory.setImageUrl("top image url");
        topCategoryRepository.save(topCategory);

        // 아무거나 하나만 찾기
        TopCategory findTopCategory = topCategoryRepository.findAll().get(0);
        Long topCategoryId = findTopCategory.getId();

        TopCategoryCreateRequest topCategoryCreateRequest = new TopCategoryCreateRequest();
        topCategoryCreateRequest.setImageUrl(findTopCategory.getImageUrl());
        topCategoryCreateRequest.setTitle(findTopCategory.getTitle());

        mockMvc.perform(post("/v1/category/top")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(topCategoryCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("topCategory/post", // restdocs 경로 설정
                        preprocessRequest(prettyPrint()), //json을 예쁘게 보여주기
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("title"), // json 필드의 역할을 예쁘게 표로 보여주기
                                fieldWithPath("imageUrl").description("category Image url")

                        ),
                        responseFields(
                                fieldWithPath("code").description("code"),
                                fieldWithPath("message").description("message")
                        ))
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void Mid_카테고리_등록() throws Exception {

        // top category 만들기
        TopCategory topCategory = new TopCategory();
        topCategory.setTitle("top category");
        topCategory.setImageUrl("top image url");
        topCategoryRepository.save(topCategory);

        // top 아무거나 하나만 찾기
        TopCategory findTopCategory = topCategoryRepository.findAll().get(0);
        Long topCategoryId = findTopCategory.getId();

        // Mid category 만들기
        MidCategory midCategory = new MidCategory();
        midCategory.setTitle("mid category");
        midCategory.setImageUrl("mid image url");
        midCategory.setTopCategory(findTopCategory);
        midCategoryRepository.save(midCategory);

        // Mid 아무거나 하나만 찾기
        MidCategory findMidCategory = midCategoryRepository.findAll().get(0);
        Long midCategoryId = findMidCategory.getId();

        MidCategoryCreateRequest midCategoryCreateRequest = new MidCategoryCreateRequest();
        midCategoryCreateRequest.setTopCategoryId(topCategoryId);
        midCategoryCreateRequest.setTitle(findMidCategory.getTitle());
        midCategoryCreateRequest.setImageUrl(findMidCategory.getImageUrl());

        mockMvc.perform(post("/v1/category/mid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(midCategoryCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("midCategory/post", // restdocs 경로 설정
                        preprocessRequest(prettyPrint()), //json을 예쁘게 보여주기
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("topCategoryId").description("상위 카테고리 id"),
                                fieldWithPath("title").description("title"), // json 필드의 역할을 예쁘게 표로 보여주기
                                fieldWithPath("imageUrl").description("category Image url"),
                                fieldWithPath("videoUrl").description("category Video url"),
                                fieldWithPath("videoText").description("category Video text")
                        ),
                        responseFields(
                                fieldWithPath("code").description("code"),
                                fieldWithPath("message").description("message")
                        ))
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void base_카테고리_등록() throws Exception {

        // top category 만들기
        TopCategory topCategory = new TopCategory();
        topCategory.setTitle("top category");
        topCategory.setImageUrl("top image url");
        topCategoryRepository.save(topCategory);

        // top 아무거나 하나만 찾기
        TopCategory findTopCategory = topCategoryRepository.findAll().get(0);
        Long topCategoryId = findTopCategory.getId();

        // Mid category 만들기
        MidCategory midCategory = new MidCategory();
        midCategory.setTitle("mid category");
        midCategory.setImageUrl("mid image url");
        midCategory.setTopCategory(findTopCategory);
        midCategoryRepository.save(midCategory);

        // Mid 아무거나 하나만 찾기
        MidCategory findMidCategory = midCategoryRepository.findAll().get(0);
        Long midCategoryId = findMidCategory.getId();

        MidCategoryCreateRequest midCategoryCreateRequest = new MidCategoryCreateRequest();
        midCategoryCreateRequest.setTopCategoryId(topCategoryId);
        midCategoryCreateRequest.setTitle(findMidCategory.getTitle());
        midCategoryCreateRequest.setImageUrl(findMidCategory.getImageUrl());

        // Basecategory 만들기
        BaseCategory baseCategory = new BaseCategory();
        baseCategory.setTitle("base title");
        baseCategory.setImageUrl("base image url");
        baseCategory.setMidCategory(findMidCategory);
        baseCategoryRepository.save(baseCategory);

        MockMultipartFile image = new MockMultipartFile("imageFile", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());

        mockMvc.perform(
                        multipart("/v2/category/base")
                                .file(image)
                                .param("title", "제목")
                                .param("midCategoryId", findMidCategory.getId().toString()))
                .andExpect(status().isOk())
                .andDo(document("baseCategory/post", // restdocs 경로 설정
                        requestPartBody("imageFile"),
                        requestParameters(
                                parameterWithName("title").description("title"), // json 필드의 역할을 예쁘게 표로 보여주기
                                parameterWithName("midCategoryId").description("중위 카테고리 id")
                        ),
                        responseFields(
                                fieldWithPath("code").description("code"),
                                fieldWithPath("message").description("message")
                        ))
                );
    }
}