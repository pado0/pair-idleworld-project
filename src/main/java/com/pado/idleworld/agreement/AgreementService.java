package com.pado.idleworld.agreement;

import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.domain.Agreement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AgreementService {

    public final AgreementRepository agreementRepository;

    public void createAgreementPolicy(Agreement.Request agreementRequestDto) {
        Agreement agreement = Agreement.builder()
                .title(agreementRequestDto.getTitle())
                .subtitle(agreementRequestDto.getSubtitle())
                .build();

        agreementRepository.save(agreement);
    }

    public Agreement findLastCreatedAgreement(){
        return agreementRepository.findTop1ByOrderByIdDesc();
    }

    public Optional<Agreement> findAgreementById(Long id) {
        return agreementRepository.findById(id);
    }

    public void deleteAgreementById(Long id) {
        agreementRepository.deleteById(id);
    }

    public void updateAgreement(Agreement.Request agreementRequest, Optional<Agreement> agreement) {
        agreement.get().setTitle(agreementRequest.getTitle());
        agreement.get().setSubtitle(agreementRequest.getSubtitle());
    }

}
