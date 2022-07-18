package com.pado.idleworld.topcategory;

import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopCategoryUpdateRequest {

    private Long id;
    private String title;

    @Lob
    private String imageUrl;
}
