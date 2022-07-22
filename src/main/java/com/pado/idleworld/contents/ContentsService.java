package com.pado.idleworld.contents;

import com.pado.idleworld.category.basecategory.BaseCategoryRepository;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import com.pado.idleworld.infra.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsService {

    private final BaseCategoryRepository baseCategoryRepository;
    private final ContentsRepository contentsRepository;
    private final AwsS3Service awsS3Service;

    public void createContents(Contents.Request contentsRequestDto) {

        // 컨텐츠를 등록하려한다 = 주문을 하려고한다. (단, 주문 할 떄 아이템 여러개 받을 수 있음)
        // 컨텐츠 생성
        Contents contents = new Contents();
        contents.setTitle(contentsRequestDto.getTitle());
        contents.setSubtitle(contentsRequestDto.getSubtitle());
        contents.setImageUrl(contentsRequestDto.getImageUrl());
        contents.setVideoUrl(contentsRequestDto.getVideoUrl());


        // 베이스 카테고리 조회
        // 조회한 베이스 카테고리로 베이스 카테고리 컨텐츠 만들
        // orderItem을 여러개 생산하는 방식으로 가보자
        for (Long id : contentsRequestDto.getBaseCategoryId()) {
            BaseCategory baseCategory = baseCategoryRepository.findById(id).get();

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

    public Contents.Request createContentsRequestDto(String title, String subtitle, Long productId, List<Long> baseCategoryIds, MultipartFile multipartImageFile, MultipartFile multipartVideoFile) {
        String imageS3Url = awsS3Service.uploadFileV1(title, multipartImageFile);
        String videoS3Url = awsS3Service.uploadFileV1(title, multipartVideoFile);

        Contents.Request contentsRequestDto = new Contents.Request();
        contentsRequestDto.setTitle(title);
        contentsRequestDto.setSubtitle(subtitle);
        contentsRequestDto.setBaseCategoryId(baseCategoryIds);
        contentsRequestDto.setProductId(productId);
        contentsRequestDto.setImageUrl(imageS3Url);
        contentsRequestDto.setVideoUrl(videoS3Url);
        return contentsRequestDto;
    }


    public void saveNewCategoryRequest(Contents.Request contentsRequestDto, Optional<Contents> findContents) {
        for (Long id : contentsRequestDto.getBaseCategoryId()) {
            BaseCategory baseCategory = baseCategoryRepository.findById(id).get();

            BaseCategoryContents bcc = new BaseCategoryContents();
            bcc.setBaseCategory(baseCategory);

            findContents.get().getBaseCategoryContents().add(bcc);
            bcc.setContents(findContents.get());
        }
    }

    public void deleteAllRelatedBaseCategoryContents(List<BaseCategoryContents> baseCategoryContents) {
        while (!baseCategoryContents.isEmpty()) {
            baseCategoryContents.remove(0);
        }
    }

    public Optional<Contents> getContentsByContentsId(Contents.Request contentsRequestDto, Long contentsId) {
        Optional<Contents> findContents = contentsRepository.findById(contentsId);
        findContents.get().setTitle(contentsRequestDto.getTitle());
        findContents.get().setSubtitle(contentsRequestDto.getSubtitle());
        findContents.get().setImageUrl(contentsRequestDto.getImageUrl());
        return findContents;
    }

    public void deleteContentsById(Long contentsId) {
        Optional<Contents> findContents = contentsRepository.findById(contentsId);
        contentsRepository.deleteById(findContents.get().getId());
    }
}
