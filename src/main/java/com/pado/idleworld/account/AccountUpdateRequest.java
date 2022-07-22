package com.pado.idleworld.account;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class AccountUpdateRequest {

    private String password;

    private String nickname;

    @Lob
    private String imageUrl;
}
