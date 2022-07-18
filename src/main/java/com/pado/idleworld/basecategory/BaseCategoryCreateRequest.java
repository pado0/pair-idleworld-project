package com.pado.idleworld.basecategory;

import com.pado.idleworld.domain.MidCategory;
import lombok.Data;
import lombok.Getter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Data
public class BaseCategoryCreateRequest {

    private String title;

    @Lob
    private String imageUrl;

    private Long midCategoryId;
}
