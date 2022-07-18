package com.pado.idleworld.contents;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import com.pado.idleworld.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ContentsController {

    private final ContentsRepository contentsRepository;
    //private final BaseCategoryRepository baseCategoryRepository;

    @PostMapping("/contents")
    public CommonResult postContentsContext(@RequestBody Contents.Request contentsRequestDto){

        // todo: di로 주입받기, 각각의 Repo에서 찾아서 세팅해주어야 함.
        Product product = new Product();

        // baseCategory id 기준으로 찾아서 베이스카테고리 리스트로 만들어줌.
        List<Optional<BaseCategory>> baseCategories = new ArrayList<>();
        List<Long> baseCategoriesId = contentsRequestDto.getBaseCategoryId();
//        for (Long id : baseCategoriesId) {
//            baseCategories.add(baseCategoryRepository.findById(id));
//        }

        // 컨텐츠를 등록하려한다 = 주문을 하려고한다. (주문 할 떄 아이템 여러개 받을 수 있음)
        // 컨텐츠를 등록할 떄 카테고리를 여러개 받아서 같이 등록하고 싶다.
        Contents contents = new Contents();
        contents.setTitle(contentsRequestDto.getTitle());
        contents.setImageUrl(contentsRequestDto.getImageUrl());

        // 카테고리 컨텐츠 생성
        BaseCategoryContents baseCategoryContents = new BaseCategoryContents();
        baseCategoryContents.setContents(contents); // 컨텐츠 세팅

        // todo: 입력에서 받아온 id로 카테고리 레퍼지토리 조회해서 긁어와야함
        /*List<Long> baseCategoryId = contentsRequestDto.getBaseCategoryId();
        for (Long baseCategory : baseCategoryId) {
            // 입력된 카테고리 id에서 하나씩 뜯어와 세팅
            // baseCategoryContents.add()
        }*/

        // categorycontentsid 리스트는 여러개 받을수 있음
//        baseCategoryContents.add(new BaseCategoryContents())

//        Contents contents = Contents.builder()
//                .title(contentsRequestDto.getTitle())
//                .subtitle(contentsRequestDto.getSubtitle())
//                .imageUrl(contentsRequestDto.getImageUrl())
//                .product(product)
//                .baseCategoryContents(baseCategoryContents)
//                .build();

        contentsRepository.save(contents);
        return new CommonResult(ResponseCode.SUCCESS);
    }

}
