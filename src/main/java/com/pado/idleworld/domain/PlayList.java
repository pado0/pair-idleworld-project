package com.pado.idleworld.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class PlayList {

    private Long id;

    @OneToOne(mappedBy = "playlist")
    private Account account;


    @OneToMany(mappedBy = "playlist")
    private List<Contents> contents = new ArrayList<>();
}
