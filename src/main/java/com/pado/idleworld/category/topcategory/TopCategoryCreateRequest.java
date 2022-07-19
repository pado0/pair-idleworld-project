package com.pado.idleworld.category.topcategory;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopCategoryCreateRequest {

    private String title;

    @Lob
    private String imageUrl;
}
