package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.*;
import org.thymeleaf.expression.Lists;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "contents")
    private List<BaseCategoryContents> baseCategoryContents = new ArrayList<>();

    // DTO
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private String title;
        private String subtitle;
        private String imageUrl;
        private Long productId;
        // todo: 일단 해보고 list로 전
        private List<Long> baseCategoryId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String title;
        private String subtitle;
    }
}
