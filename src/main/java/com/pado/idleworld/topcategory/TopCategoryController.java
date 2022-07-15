package com.pado.idleworld.topcategory;

import com.pado.idleworld.account.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class TopCategoryController {


    @GetMapping("/category/top")
    public String topCategoryCreate(Model model) {
        model.addAttribute(new SignUpForm());

        return "topcategory/get";
    }
}
