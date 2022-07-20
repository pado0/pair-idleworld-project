package com.pado.idleworld.contents;

import com.pado.idleworld.category.basecategory.BaseCategoryRepository;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import com.pado.idleworld.infra.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;
    private final ContentsRepository contentsRepository;
    private final BaseCategoryRepository baseCategoryRepository;
    private final BaseCategoryContentsRepository baseCategoryContentsRepository;
    private final EntityManager em;
    private final AwsS3Service awsS3Service;

    // todo: product 쪽 개발되면 세팅해주기
    @PostMapping("/v1/contents")
    public CommonResult postContentsContext(@RequestBody Contents.Request contentsRequestDto){

        contentsService.createContents(contentsRequestDto);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    // aws 이미지 업로드 적용된 api
    @PostMapping("/v2/contents")
    public CommonResult postContentsContextV2(@RequestParam String title,
                                              @RequestParam String subtitle,
                                              @RequestParam Long productId,
                                              @RequestParam(value="baseCategoryId", required=false, defaultValue="") List<Long> baseCategoryIds,
                                              @RequestPart(value = "file") MultipartFile multipartFile){


        String imageS3Url = awsS3Service.uploadFileV1(title, multipartFile);
        Contents.Request contentsRequestDto = new Contents.Request();
        contentsRequestDto.setTitle(title);
        contentsRequestDto.setSubtitle(subtitle);
        contentsRequestDto.setBaseCategoryId(baseCategoryIds);
        contentsRequestDto.setProductId(productId);
        contentsRequestDto.setImageUrl(imageS3Url);

        contentsService.createContents(contentsRequestDto);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    // 카테고리 기준으로 하위 컨텐츠 조회
    @GetMapping("/v1/{categoryId}/contents")
    public DataResult getCategoryContentsList(@PathVariable("categoryId") Long categoryId){

        List<ContentsResponseDto> contentsResponseDtos = baseCategoryContentsRepository.findContentsResponseDtoByBaseCategoryId(categoryId);
        return new DataResult(ResponseCode.SUCCESS, contentsResponseDtos);
    }

    // 컨텐츠 전체 조회
    @GetMapping("/v1/contents")
    public DataResult getAllContents(){

        List<ContentsResponseDto> contentsResponseDtos = baseCategoryContentsRepository.findContentsResponseDto();
        return new DataResult(ResponseCode.SUCCESS, contentsResponseDtos);
    }

    // 컨텐츠 수정 - 카테고리 다시 다 입력받아야 함
    @Transactional
    @PutMapping("/v1/contents/{contentsId}")
    public CommonResult putContentsContext(@RequestBody Contents.Request contentsRequestDto,
                                           @PathVariable("contentsId") Long contentsId){
        Optional<Contents> findContents = contentsRepository.findById(contentsId);
        findContents.get().setTitle(contentsRequestDto.getTitle());
        findContents.get().setSubtitle(contentsRequestDto.getSubtitle());
        //findContents.get().setImageUrl(contentsRequestDto.getImageUrl());

        List<BaseCategoryContents> baseCategoryContents = findContents.get().getBaseCategoryContents();

        // 부모고아 관계를 활용한 삭제
        while (!baseCategoryContents.isEmpty()) {
            baseCategoryContents.remove(0);
        }

        for (Long id : contentsRequestDto.getBaseCategoryId()) {
            BaseCategory baseCategory = baseCategoryRepository.findOneById(id);

            BaseCategoryContents bcc = new BaseCategoryContents();
            bcc.setBaseCategory(baseCategory);

            findContents.get().getBaseCategoryContents().add(bcc);
            bcc.setContents(findContents.get());
        }

        return new CommonResult(ResponseCode.SUCCESS);
    }

    // 컨텐츠 삭제 - 컨텐츠 삭제시 baseCategory 내 관련 컨텐츠 데이터 모두 삭제 (고아객체)
    @Transactional
    @DeleteMapping("/v1/contents/{contentsId}")
    public CommonResult putContentsContext(@PathVariable("contentsId") Long contentsId){

        Optional<Contents> findContents = contentsRepository.findById(contentsId);
        contentsRepository.deleteById(findContents.get().getId());

        return new CommonResult(ResponseCode.SUCCESS);
    }


}
