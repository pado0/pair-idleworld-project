package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BaseCategory extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid_category_id")
    private MidCategory midCategory;

}
