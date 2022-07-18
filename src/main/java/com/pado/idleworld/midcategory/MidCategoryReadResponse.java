package com.pado.idleworld.midcategory;

import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MidCategoryReadResponse {

    private Long id;

    private String title;

    @Lob
    private String imageUrl;
    @Lob
    private String videoUrl;
    private String videoText;
    private Long topCategoryId;
}
