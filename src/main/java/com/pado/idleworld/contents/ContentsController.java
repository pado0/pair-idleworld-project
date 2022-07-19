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

    private final ContentsService contentsService;
    private final ContentsRepository contentsRepository;
    private final BaseCategoryRepository baseCategoryRepository;

    // todo: product 쪽 개발되면 세팅해주기
    @PostMapping("/contents")
    public CommonResult postContentsContext(@RequestBody Contents.Request contentsRequestDto){

        contentsService.createContents(contentsRequestDto);
        return new CommonResult(ResponseCode.SUCCESS);
    }



}
