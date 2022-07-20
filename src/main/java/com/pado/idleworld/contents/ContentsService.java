package com.pado.idleworld.contents;

import com.pado.idleworld.category.basecategory.BaseCategoryRepository;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsService {

    private final BaseCategoryRepository baseCategoryRepository;
    private final ContentsRepository contentsRepository;

    public void createContents(Contents.Request contentsRequestDto) {

        // 컨텐츠를 등록하려한다 = 주문을 하려고한다. (단, 주문 할 떄 아이템 여러개 받을 수 있음)
        // todo: 여기서 contents는 비영속인데, 왜 빌더패턴으로 대체가 되지 않는지?
//        Contents contents = Contents.builder()
//                .title(contentsRequestDto.getTitle())
//                .subtitle(contentsRequestDto.getSubtitle())
//                .imageUrl(contentsRequestDto.getImageUrl())
//                .build();

        // 컨텐츠 생성
        Contents contents = new Contents();
        contents.setTitle(contentsRequestDto.getTitle());
        contents.setSubtitle(contentsRequestDto.getSubtitle());
        contents.setImageUrl(contentsRequestDto.getImageUrl());


        // 베이스 카테고리 조회
        // 조회한 베이스 카테고리로 베이스 카테고리 컨텐츠 만들
        // orderItem을 여러개 생산하는 방식으로 가보자
        for (Long id : contentsRequestDto.getBaseCategoryId()) {
            BaseCategory baseCategory = baseCategoryRepository.findOneById(id);

            // createBaseCategoryContents, contents와 베이스카테고리 컨텐츠 관계
            BaseCategoryContents baseCategoryContents = new BaseCategoryContents();
            baseCategoryContents.setBaseCategory(baseCategory);

            // content와 연관관계 맺기
            // 이 .add 코드가 안들어가면 set한 컨텐츠가 1차 캐시에서 바로 튀어나옴.
            // 이때 jpa가 fk도 모르고 값 세팅이 되지 않아서 조회가 안된다.
            // 일단 add 해서 메모리에 넣어놓고 컨텐츠를 세팅해준다.
            // contents를 get해오면서 baseCategory도 cascade
            contents.getBaseCategoryContents().add(baseCategoryContents);
            baseCategoryContents.setContents(contents);
        }

        contentsRepository.save(contents);
    }
}
