package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.*;
import org.thymeleaf.expression.Lists;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    // baseCategory contents는 컨텐츠 등록시에만 생성되므로 cascade 설정
    @OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BaseCategoryContents> baseCategoryContents = new ArrayList<>();

    @Lob
    private String videoUrl;

    // DTO
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{

        private String title;

        @NotBlank
        private String subtitle;

        private String imageUrl;

        private Long productId;

        private List<Long> baseCategoryId;

        private String videoUrl;
    }

}
