package com.pado.idleworld.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class MidCategory extends BaseEntity{

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String imageUrl;
    @Lob
    private String videoUrl;
    private String videoText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_category_id")
    private TopCategory topCategory;

    @OneToMany(mappedBy = "midCategory")
    private List<BaseCategory> baseCategories = new ArrayList<>();



}
