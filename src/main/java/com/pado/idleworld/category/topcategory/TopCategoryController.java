package com.pado.idleworld.category.topcategory;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.TopCategory;
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
public class TopCategoryController {

    private final TopCategoryService topCategoryService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/category/top")
    public CommonResult topCategoryCreate(@RequestBody @Valid TopCategoryCreateRequest request) {
        topCategoryService.createTopCategory(request);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/category/top")
    public DataResult topCategoryRead() {
        List<TopCategory> topCategories = topCategoryService.findTopCategories();
        List<TopCategoryReadResponse> result = topCategories.stream()
                .map(m->new TopCategoryReadResponse(m.getId(),m.getTitle(), m.getImageUrl()))
                .collect(Collectors.toList());

        return new DataResult(ResponseCode.SUCCESS, result);
    }

    //todo 업데이트가 안됨, JPA return쪽 봐보자
    @Secured("ROLE_ADMIN")
    @PutMapping("/category/top")
    public CommonResult topCategoryUpdate(@RequestBody @Valid TopCategoryUpdateRequest request) {
        topCategoryService.updateTopCategory(request);
        //topCategoryService.findOneTopCategory(request.getId());
        return new CommonResult(ResponseCode.SUCCESS);
    }
}
