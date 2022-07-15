package com.pado.idleworld.agreement;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.Agreement;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AgreementController {

    private final AgreementRepository agreementRepository;
    private final AgreementService agreementService;

    // Admin 이용 약관 내용 CRUD

    @PostMapping("/agreement")
    public CommonResult postAgreementPolicy(@RequestBody Agreement.Request agreementRequestDto) {

        agreementService.createAgreementPolicy(agreementRequestDto);

        return new CommonResult(ResponseCode.SUCCESS);
    }


    // 최신 약관 조회기능
    @GetMapping("/agreement")
    public DataResult getLatestAgreementPolicy(){
        Agreement agreement = agreementRepository.findTop1ByOrderByIdDesc();
        return new DataResult(ResponseCode.SUCCESS, agreement);
    }

    @PutMapping("/agreement/{id}")
    @Transactional
    public CommonResult putAgreementPolicy(@RequestBody Agreement.Request agreementRequest,
                                           @PathVariable("id") Long id) {

        Optional<Agreement> agreement = agreementRepository.findById(id);
        agreement.get().setTitle(agreementRequest.getTitle());
        agreement.get().setSubtitle(agreementRequest.getSubtitle());

        return new CommonResult(ResponseCode.SUCCESS);
    }

}
