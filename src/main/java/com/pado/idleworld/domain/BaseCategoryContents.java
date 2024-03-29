package com.pado.idleworld.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class BaseCategoryContents {

    @Id
    @GeneratedValue
    private Long id;

    // 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    // 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_category_id")
    private BaseCategory baseCategory;

}
