package com.pado.idleworld.category.basecategory;

import com.pado.idleworld.domain.MidCategory;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Data
public class BaseCategoryUpdateRequest {

    private Long id;

    private String title;

    @Lob
    private String imageUrl;

    private Long midCategoryId;
}
