package com.pado.idleworld.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contents extends BaseEntity{

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
