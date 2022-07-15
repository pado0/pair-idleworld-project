package com.pado.idleworld.agreement;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.Agreement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AgreementController {

    private final AgreementRepository agreementRepository;

    // Admin 이용 약관 내용 CRUD

    @PostMapping("/agreement")
    public CommonResult postAgreementPolicy(@RequestBody Agreement.Request agreementRequestDto) {

        Agreement agreement = Agreement.builder()
                .title(agreementRequestDto.getTitle())
                .subtitle(agreementRequestDto.getSubtitle())
                .build();

        agreementRepository.save(agreement);

        // todo : 공통코드 enum 리팩토링
        return new CommonResult(ResponseCode.SUCCESS);
    }

    @GetMapping("/agreement")
    public DataResult getLatestAgreementPolicy(){
        agreementRepository.findById(agreementRepository.count() - 1);
        return new DataResult(ResponseCode.SUCCESS);
    }

}
