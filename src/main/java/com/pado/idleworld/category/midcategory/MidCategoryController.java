package com.pado.idleworld.category.midcategory;


import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.MidCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MidCategoryController {

    private final MidCategoryService midCategoryService;

    //todo : topCategoryId가 틀렸을때 null로 생성된다.
    @Secured("ROLE_ADMIN")
    @PostMapping("/category/mid")
    public CommonResult midCategoryCreate(@RequestBody @Valid MidCategoryCreateRequest request) {
        midCategoryService.createMidCategory(request);

        return new CommonResult(ResponseCode.SUCCESS);
    }

    //todo : 미드카테고리 -> 미드카테고리리드리스폰스 서비스단으로
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/category/mid")
    public DataResult midCategoryRead() {
        List<MidCategory> midCategories = midCategoryService.findMidCategories();
        List<MidCategoryReadResponse> result = midCategories.stream()
                .map(s -> new MidCategoryReadResponse(s.getId(), s.getTitle(), s.getImageUrl(), s.getVideoUrl(), s.getVideoText(), s.getTopCategory().getId()))
                .collect(Collectors.toList());
        return new DataResult<>(ResponseCode.SUCCESS, result);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/category/mid")
    public CommonResult midCategoryUpdate(@RequestBody @Valid MidCategoryUpdateRequest request) {
        midCategoryService.updateMidCategory(request);
        return new CommonResult<>(ResponseCode.SUCCESS);
    }
}
