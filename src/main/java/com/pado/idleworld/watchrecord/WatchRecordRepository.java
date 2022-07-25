package com.pado.idleworld.watchrecord;

import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.domain.WatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchRecordRepository extends JpaRepository<WatchRecord, Long> {
    Long findByAccountId(Long accountId);
}
