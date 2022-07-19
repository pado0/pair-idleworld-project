package com.pado.idleworld.contents;

import com.pado.idleworld.basecategory.BaseCategoryRepository;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import com.pado.idleworld.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
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
    private final BaseCategoryRepository baseCategoryRepository;

    // todo: product 쪽 개발되면 세팅해주기
    @Transactional
    @PostMapping("/contents")
    public CommonResult postContentsContext(@RequestBody Contents.Request contentsRequestDto){

        // 컨텐츠를 등록하려한다 = 주문을 하려고한다. (단, 주문 할 떄 아이템 여러개 받을 수 있음)
        // 컨텐츠 생성
        // 컨텐츠를 등록할 떄 카테고리를 여러개 받아서 같이 등록하고 싶다.
        Contents contents = new Contents();
        contents.setTitle(contentsRequestDto.getTitle());
        contents.setSubtitle(contentsRequestDto.getSubtitle());
        contents.setImageUrl(contentsRequestDto.getImageUrl());


        // 카테고리 Id를 리스트로 받음
        // baseCategory id 기준으로 찾아서 베이스카테고리 리스트로 만들어줌.
        List<Long> baseCategoriesId = contentsRequestDto.getBaseCategoryId();

        // 베이스 카테고리 조회
        List<BaseCategory> baseCategories = new ArrayList<>();
        for (Long id : baseCategoriesId) {
            baseCategories.add(baseCategoryRepository.findOneById(id));
        }

        // 조회한 베이스 카테고리로 베이스 카테고리 컨텐츠 만들
        // orderItem을 여러개 생산하는 방식으로 가보자
        List<BaseCategoryContents> baseCategoryContentsList = new ArrayList<>();
        for (BaseCategory baseCategory : baseCategories) {

            // createBaseCategoryContents, contents와 베이스카테고리 컨텐츠 관계
            BaseCategoryContents baseCategoryContents = new BaseCategoryContents();
            baseCategoryContents.setBaseCategory(baseCategory);

            // content와 연관관계 맺기
            // 이 아래코드가 들어가야 baseCategory 엔티티가 영속화 되면서 db에 세팅된다.
            // contents를 get해오면서 baseCategory도 cascade
            contents.getBaseCategoryContents().add(baseCategoryContents);
            baseCategoryContents.setContents(contents);

        }

        contentsRepository.save(contents);
        return new CommonResult(ResponseCode.SUCCESS);
    }

}
