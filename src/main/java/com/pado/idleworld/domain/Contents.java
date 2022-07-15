package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contents extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String imageUrl;

    private String subtitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private PlayList playList;

}
