package com.pado.idleworld.contents;

import com.pado.idleworld.category.basecategory.BaseCategoryRepository;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.PageResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.BaseCategory;
import com.pado.idleworld.domain.BaseCategoryContents;
import com.pado.idleworld.domain.Contents;
import com.pado.idleworld.infra.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContentsController {
    private final ContentsService contentsService;
    private final BaseCategoryContentsRepository baseCategoryContentsRepository;

    @PostMapping("/v1/contents")
    public CommonResult postContentsContext(@RequestBody @Valid Contents.Request contentsRequestDto){

        contentsService.createContents(contentsRequestDto);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    @PostMapping("/v3/contents")
    public CommonResult postContentsContextV3(@RequestParam String title,
                                              @RequestParam String subtitle,
                                              @RequestParam Long productId,
                                              @RequestParam(value="baseCategoryId", required=false, defaultValue="") List<Long> baseCategoryIds,
                                              @RequestPart(value = "imageFile") MultipartFile multipartImageFile,
                                              @RequestPart(value = "videoFile") MultipartFile multipartVideoFile){

        Contents.Request contentsRequestDto = contentsService.createContentsRequestDto(title, subtitle, productId, baseCategoryIds, multipartImageFile, multipartVideoFile);
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

    // 컨텐츠 전체 조회 with paging
    @GetMapping("/v2/contents")
    public PageResult getAllContentsV2(@RequestParam int page,
                                         @RequestParam int size){

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ContentsResponseDto> contentsResponseDtos = baseCategoryContentsRepository.findContentsResponseDtoWithPaging(pageRequest);


    return new PageResult(ResponseCode.SUCCESS, contentsResponseDtos.get(),
            contentsResponseDtos.getPageable().getPageNumber(),
            contentsResponseDtos.getPageable().getPageSize(),
            contentsResponseDtos.getTotalPages());
    }


    // 컨텐츠 수정 - 카테고리 다시 다 입력받아야 함
    @PutMapping("/v1/contents/{contentsId}")
    public CommonResult putContentsContext(@RequestBody @Valid Contents.Request contentsRequestDto,
                                           @PathVariable("contentsId") Long contentsId){

        Optional<Contents> findContents = contentsService.getContentsByContentsId(contentsRequestDto, contentsId);
        List<BaseCategoryContents> baseCategoryContents = findContents.get().getBaseCategoryContents();

        // 부모고아 관계를 활용한 삭제
        contentsService.deleteAllRelatedBaseCategoryContents(baseCategoryContents);
        // 수정으로 들어온 카테고리 다시 넣기
        contentsService.saveNewCategoryRequest(contentsRequestDto, findContents);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    @PutMapping("/v3/contents/{contentsId}")
    public CommonResult putContentsContextV2(@RequestParam String title,
                                             @RequestParam String subtitle,
                                             @RequestParam Long productId,
                                             @RequestParam(value="baseCategoryId", required=false, defaultValue="") List<Long> baseCategoryIds,
                                             @RequestPart(value = "imageFile") MultipartFile multipartImageFile,
                                             @RequestPart(value = "videoFile") MultipartFile multipartVideoFile,
                                             @PathVariable("contentsId") Long contentsId){


        Contents.Request contentsRequestDto = contentsService.createContentsRequestDto(title, subtitle, productId, baseCategoryIds, multipartImageFile, multipartVideoFile);
        //contentsService.createContents(contentsRequestDto);

        Optional<Contents> findContents = contentsService.getContentsByContentsId(contentsRequestDto, contentsId);
        List<BaseCategoryContents> baseCategoryContents = findContents.get().getBaseCategoryContents();

        // 부모고아 관계를 활용한 삭제
        contentsService.deleteAllRelatedBaseCategoryContents(baseCategoryContents);
        // 수정으로 들어온 카테고리 다시 넣기
        contentsService.saveNewCategoryRequest(contentsRequestDto, findContents);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    // 컨텐츠 삭제 - 컨텐츠 삭제시 baseCategory 내 관련 컨텐츠 데이터 모두 삭제 (고아객체)
    @DeleteMapping("/v1/contents/{contentsId}")
    public CommonResult putContentsContext(@PathVariable("contentsId") Long contentsId){

        contentsService.deleteContentsById(contentsId);

        return new CommonResult(ResponseCode.SUCCESS);
    }


}
