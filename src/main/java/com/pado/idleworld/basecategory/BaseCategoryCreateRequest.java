package com.pado.idleworld.basecategory;

import com.pado.idleworld.domain.MidCategory;
import lombok.Data;
import lombok.Getter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

//todo - midCategoryId 틀리면 null로 생성됨
@Data
public class BaseCategoryCreateRequest {

    private String title;

    @Lob
    private String imageUrl;

    private Long midCategoryId;
}
