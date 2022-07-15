package com.pado.idleworld.agreement;

import com.pado.idleworld.domain.Agreement;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

}
