package com.pado.idleworld.category.basecategory;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.infra.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BaseCategoryController {

    private final BaseCategoryService baseCategoryService;
    private final AwsS3Service awsS3Service;

    @PostMapping("/v1/category/base")
    public CommonResult baseCategoryCreate(@RequestBody @Valid BaseCategoryCreateRequest request) {
        baseCategoryService.createBaseCategory(request);
        return new CommonResult(ResponseCode.SUCCESS);
    }

    @PostMapping("/v2/category/base")
    public CommonResult baseCategoryCreateV2(@RequestParam String title,
                                             @RequestParam Long midCategoryId,
                                             @RequestPart(value = "imageFile") MultipartFile multipartImageFile) {

        BaseCategoryCreateRequest baseCategoryCreateRequest = new BaseCategoryCreateRequest();
        String imageS3Url = awsS3Service.uploadFileV1(title, multipartImageFile);

        baseCategoryCreateRequest.setTitle(title);
        baseCategoryCreateRequest.setMidCategoryId(midCategoryId);
        baseCategoryCreateRequest.setImageUrl(imageS3Url);

        baseCategoryService.createBaseCategory(baseCategoryCreateRequest);
        return new CommonResult(ResponseCode.SUCCESS);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/v1/category/base")
    public DataResult baseCategoryRead() {
        List<BaseCategoryReadResponse> result = baseCategoryService.findBaseCategories();
        return new DataResult(ResponseCode.SUCCESS, result);

    }

    @PutMapping("/v1/category/base")
    public CommonResult basesCategoryUpdate(@RequestBody @Valid BaseCategoryUpdateRequest request) {
        baseCategoryService.updateBaseCategory(request);

        return new CommonResult(ResponseCode.SUCCESS);
    }
}
