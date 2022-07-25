package com.pado.idleworld.domain;

import com.pado.idleworld.common.BaseEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @NotBlank
    @Length(min = 3, max = 20)
    private String email;

    @NotBlank
    //@Length(min = 8, max = 20)
    private String password;

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-z\\d가-힣]+\\s?[a-zA-z\\d가-힣]+$")
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

    private String provider;
    private String providerId;


    private boolean blocked;
    private int failCount;

    public void addFailCount() {
        failCount++;
    }





    private Long watchCount;


}
