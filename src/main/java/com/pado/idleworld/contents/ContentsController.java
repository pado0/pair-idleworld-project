package com.pado.idleworld.contents;

import com.pado.idleworld.category.basecategory.BaseCategoryRepository;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.Contents;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ContentsController {

    private final ContentsService contentsService;
    private final ContentsRepository contentsRepository;
    private final BaseCategoryRepository baseCategoryRepository;
    private final BaseCategoryContentsRepository baseCategoryContentsRepository;

    // todo: product 쪽 개발되면 세팅해주기
    @PostMapping("/contents")
    public CommonResult postContentsContext(@RequestBody Contents.Request contentsRequestDto){

        contentsService.createContents(contentsRequestDto);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    // 카테고리 기준으로 하위 컨텐츠 조회
    @GetMapping("/{categoryId}/contents")
    public DataResult getContentsList(@PathVariable("categoryId") Long categoryId){

        List<ContentsResponseDto> contentsResponseDto = baseCategoryContentsRepository.findContentsResponseDtoByBaseCategoryId(categoryId);
        return new DataResult(ResponseCode.SUCCESS, contentsResponseDto);
    }




}
