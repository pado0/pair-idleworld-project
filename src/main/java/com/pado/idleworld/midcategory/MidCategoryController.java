package com.pado.idleworld.midcategory;


import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.MidCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Lob;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MidCategoryController {

    private final MidCategoryService midCategoryService;

    //todo : topCategoryId가 틀렸을때 null로 생성된다.
    @PostMapping("/category/mid")
    public CommonResult midCategoryCreate(@RequestBody @Valid MidCategoryCreateRequest request) {
        midCategoryService.createMidCategory(request);

        return new CommonResult(ResponseCode.SUCCESS);
    }

    @GetMapping("/category/mid")
    public DataResult midCategoryRead() {
        List<MidCategory> midCategories = midCategoryService.findMidCategories();
        List<MidCategoryReadResponse> result = midCategories.stream()
                .map(s -> new MidCategoryReadResponse(s.getId(), s.getTitle(), s.getImageUrl(), s.getVideoUrl(), s.getVideoText(), s.getTopCategory().getId()))
                .collect(Collectors.toList());
        return new DataResult<>(ResponseCode.SUCCESS, result);
    }



}
