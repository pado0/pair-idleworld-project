package com.pado.idleworld.basecategory;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class BaseCategoryController {

    private final BaseCategoryService baseCategoryService;

    @PostMapping("/category/base")
    public CommonResult baseCategoryCreate(@RequestBody @Valid BaseCategoryCreateRequest request) {
        baseCategoryService.createBaseCategory(request);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    @GetMapping("/category/base")
    public DataResult baseCategoryRead() {
        baseCategoryService.findBaseCategories();
        return null;
    }
}
