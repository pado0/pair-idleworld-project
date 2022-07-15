package com.pado.idleworld.topcategory;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.TopCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TopCategoryController {

    private final TopCategoryService topCategoryService;

    @PostMapping("/category/top")
    public CommonResult topCategoryCreate(@RequestBody @Valid TopCategoryCreateRequest request) {
        topCategoryService.createTopCategory(request);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    @GetMapping("/category/top")
    public DataResult topCategoryRead() {
        List<TopCategory> topCategories = topCategoryService.findTopCategories();
        List<TopCategoryReadResponse> result = topCategories.stream()
                .map(m->new TopCategoryReadResponse(m.getId(),m.getTitle(), m.getImageUrl()))
                .collect(Collectors.toList());

        return new DataResult(ResponseCode.SUCCESS, result);
    }

    //todo 업데이트가 안됨, JPA return쪽 봐보자
    @PutMapping("/category/top")
    public CommonResult topCategoryUpdate(@RequestBody @Valid TopCategoryUpdateRequest request) {
        topCategoryService.update(request);
        topCategoryService.findOneTopCategory(request.getId());
        return new CommonResult(ResponseCode.SUCCESS);
    }
}
