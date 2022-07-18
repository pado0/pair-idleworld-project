package com.pado.idleworld.topcategory;

import lombok.*;

import javax.persistence.Lob;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopCategoryReadResponse {

    private Long topCategoryId;
    private String title;
    @Lob
    private String imageUrl;


}
