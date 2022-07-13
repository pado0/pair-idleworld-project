package com.pado.idleworld.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class BaseCategory extends BaseEntity{

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid_category_id")
    private MidCategory midCategory;

}
