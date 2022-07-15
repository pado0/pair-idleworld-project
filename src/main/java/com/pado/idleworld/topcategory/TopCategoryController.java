package com.pado.idleworld.topcategory;

import com.pado.idleworld.account.SignUpForm;
import com.pado.idleworld.domain.TopCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Lob;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TopCategoryController {

    TopCategoryService topCategoryService;

//    @PostMapping("/category/top")
//    public TopCategoryCreateResponse topCategoryCreate(@RequestBody @Valid TopCategoryCreateRequest request) {
//        TopCategory topCategory = topCategoryService.createTopCategory(request);
//
//    }

    private String title;
    @Lob
    private String imageUrl;

    @GetMapping("/category/top")
    public String topCategoryRead(Model model) {
        model.addAttribute(new SignUpForm());

        return "topcategory/get";
    }
}
