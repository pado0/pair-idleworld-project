package com.pado.idleworld.agreement;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.common.Result;
import com.pado.idleworld.domain.Agreement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AgreementController {

    private final AgreementRepository agreementRepository;

    // Admin 이용 약관 내용 CRUD

    @PostMapping("/agreement")
    public Result<Agreement.Response> postAgreementPolicy(@RequestBody Agreement.Request agreementRequestDto) {

        Agreement agreement = Agreement.builder()
                .title(agreementRequestDto.getTitle())
                .subtitle(agreementRequestDto.getSubtitle())
                .build();

        agreementRepository.save(agreement);

        // todo : 공통코드 enum 리팩토링
        return new Result<Agreement.Response>(HttpStatus.OK, "성공", new Agreement.Response(agreement.getTitle()));
    }


}
