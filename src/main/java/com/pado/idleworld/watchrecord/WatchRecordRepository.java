package com.pado.idleworld.watchrecord;

import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.domain.WatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WatchRecordRepository extends JpaRepository<WatchRecord, Long> {
    List<WatchRecord> findByAccountId(Long accountId);
    @Query("select w from WatchRecord w where w.account.id = :account_id and w.isWatchRecordExpired = false")
    List<WatchRecord> findByAccountIdWhereBoolFalse(@Param("account_id")Long accountId);
}

