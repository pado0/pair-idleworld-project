package com.pado.idleworld.topcategory;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopCategoryCreateResponse {

    private Long topCategoryId;
    private String title;
    @Lob
    private String imageUrl;


}
