package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
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

    @Enumerated(EnumType.STRING)
    private AccountRole role;
    //계정 등급 [ADMIN, NORMAL]
}
