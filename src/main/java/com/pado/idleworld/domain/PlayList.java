package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class PlayList extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "playList", fetch = FetchType.LAZY)
    private Account account;


    @OneToMany(mappedBy = "playList")
    private List<Contents> contents = new ArrayList<>();
}
