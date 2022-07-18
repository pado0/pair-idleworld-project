package com.pado.idleworld.basecategory;

import com.pado.idleworld.domain.MidCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

//Todo - @Getter, @Setter 없으면 에러발생,  "org.springframework.http.converter.HttpMessageConversionException: Type definition error:
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCategoryReadResponse {

    private Long id;

    private String title;

    @Lob
    private String imageUrl;

    private Long midCategoryId;
}
