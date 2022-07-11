package com.pado.idleworld.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class TopCategory extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String imageUrl;

    @OneToMany(mappedBy = "topCategory")
    private List<MidCategory> midCategories = new ArrayList<>();

}
