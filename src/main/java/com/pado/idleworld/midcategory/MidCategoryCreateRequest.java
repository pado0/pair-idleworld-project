package com.pado.idleworld.midcategory;


import com.pado.idleworld.domain.TopCategory;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MidCategoryCreateRequest {

    private String title;

    @Lob
    private String imageUrl;
    @Lob
    private String videoUrl;
    private String videoText;

    private Long topCategoryId;
}
