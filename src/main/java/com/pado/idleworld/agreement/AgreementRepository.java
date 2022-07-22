package com.pado.idleworld.agreement;

import com.pado.idleworld.domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    // id 기준 내림차순 정렬하고 맨 위에껄 가져온다.
    Agreement findTop1ByOrderByIdDesc();

}
