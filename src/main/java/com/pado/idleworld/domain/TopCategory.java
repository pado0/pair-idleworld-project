package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class TopCategory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String imageUrl;

    @OneToMany(mappedBy = "topCategory")
    private List<MidCategory> midCategories = new ArrayList<>();

}
