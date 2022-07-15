package com.pado.idleworld.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Email
    private String email;

    private String password;

    private String nickname;

    @Lob
    private String imageUrl;

    @OneToMany(mappedBy = "account")
    private List<AccountProduct> accountProducts = new ArrayList<>();

    private boolean agree;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="playlist_id")
    private PlayList playList;
}
