package com.pado.idleworld.account;

import com.pado.idleworld.domain.AccountProduct;
import com.pado.idleworld.domain.PlayList;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AccountInfoResponse {

    private String email;

    private String nickname;

    @Lob
    private String imageUrl;

    private boolean agree;

    private Long playListId;
}
