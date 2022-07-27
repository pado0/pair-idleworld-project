package com.pado.idleworld.agreement;

import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.Agreement;
import com.pado.idleworld.exception.AgreementNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AgreementController {
    private final AgreementService agreementService;

    // Admin 이용 약관 내용 CRUD

    @Secured("ROLE_ADMIN")
    @PostMapping("/agreement")
    public CommonResult postAgreementPolicy(@RequestBody @Valid Agreement.Request agreementRequestDto) {

        agreementService.createAgreementPolicy(agreementRequestDto);

        return new CommonResult(ResponseCode.SUCCESS);
    }


    // 최신 약관 조회기능
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/agreement")
    public DataResult getLatestAgreementPolicy(){

        Agreement agreement = agreementService.findLastCreatedAgreement();
        if(agreement == null) throw new AgreementNotExistsException();
        else return new DataResult(ResponseCode.SUCCESS, agreement);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/agreement/{id}")
    public CommonResult putAgreementPolicy(@RequestBody Agreement.Request agreementRequest,
                                           @PathVariable("id") Long id) {

        Optional<Agreement> agreement = agreementService.findAgreementById(id);
        if(agreement.isEmpty()) throw new NoSuchElementException();

        agreementService.updateAgreement(agreementRequest, agreement);

        return new CommonResult(ResponseCode.SUCCESS);
    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping("/agreement/{id}")
    public CommonResult deleteAgreementPolicy(@PathVariable("id") Long id) {

        Optional<Agreement> agreement = agreementService.findAgreementById(id);
        if(agreement.isEmpty()) throw new NoSuchElementException();

        agreementService.deleteAgreementById(id);
        return new CommonResult(ResponseCode.SUCCESS);
    }

}
