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
public class Product extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String code;
    private Long price;
    private String name;

    @OneToMany(mappedBy = "product")
    private List<AccountProduct> accountProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Contents> contents = new ArrayList<>();

}
