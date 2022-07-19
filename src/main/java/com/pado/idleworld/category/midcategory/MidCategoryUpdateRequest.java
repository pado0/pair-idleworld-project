package com.pado.idleworld.category.midcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MidCategoryUpdateRequest {

    private Long id;

    private String title;

    @Lob
    private String imageUrl;
    @Lob
    private String videoUrl;
    private String videoText;
    private Long topCategoryId;
}
