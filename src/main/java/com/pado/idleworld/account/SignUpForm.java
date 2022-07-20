package com.pado.idleworld.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpForm {

    @Email
    @NotBlank
    @Length(min = 3, max = 20)
    private String email;

    //@NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @Lob
    private String imageUrl;

    private boolean agree;
    private String provider;
    private String providerId;


}
