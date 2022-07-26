package com.pado.idleworld.watchrecord;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.contents.ContentsRepository;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.domain.Contents;
import com.pado.idleworld.domain.WatchRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WatchRecordController {

    private final WatchRecordRepository watchRecordRepository;
    private final AccountRepository accountRepository;
    private final ContentsRepository contentsRepository;


    // 시청이력 등록
    @Transactional
    @PostMapping("/v1/watch-record/{contentsId}")
    public CommonResult recordPost(Principal principal,
                             @PathVariable("contentsId") Long contentsId){

        // Post를 보낸 회원의 정보를 가져온다
        String accountEmail = principal.getName();
        Account findAccount = accountRepository.findByEmail(accountEmail);

        // 컨텐츠 id 기반으로 시청 컨텐츠를 가져온다
        Optional<Contents> findContents = contentsRepository.findById(contentsId);

        // 해당 회원의 시청이력을 watchrecord 테이블에 업데이트한다. 어떤 컨텐츠를, 몇 회 봤는지.
        WatchRecord watchRecord = new WatchRecord();
        watchRecord.setContents(findContents.get());

        // 시청이력 갱신 날짜 설정. 테스트로 2분뒤 만료
        watchRecord.setExpiredAt(LocalDateTime.now().plusMinutes(2));
        watchRecord.setWatchRecordExpired(false);

        // 아직 메모리에 밖에 없으므로 메모리에 watchRecord 를 세팅해준다.
        findAccount.getWatchRecords().add(watchRecord);
        watchRecord.setAccount(findAccount);

        return new CommonResult(ResponseCode.SUCCESS);
    }

    // 시청이력 조회
    // todo: 이쪽 쿼리만 잘짜면 된다!!
    @Transactional
    @GetMapping("/v1/watch-record")
    public DataResult recordGet(Principal principal) { // repo 조회시마다 쿼리 총 네개 나감

        // 계정정보 읽어와서
        Account findAccount = accountRepository.findByEmail(principal.getName());

        // watchRecordList 중 만료되지 않은 것 다 읽어옴 (쿼리로 반영, where isExpired = false)
        List<WatchRecord> watchRecordList = watchRecordRepository.findByAccountIdWhereBoolFalse(findAccount.getId());

        // 지금 시간과 만료시간 비교해서 만료처리 필요한지 확인. set할 때 flush 됨
        for (WatchRecord watchRecord : watchRecordList) {
            // 만료라면 true로 변경
            if ( watchRecord.getExpiredAt().isBefore(LocalDateTime.now()) ){
                watchRecord.setWatchRecordExpired(true);
            }
        }

        // 만료처리 필요한것은 만료로 바꾸고, 다시 읽어옴. 보통 이런 만료처리는 어느 시점에, 누가 주체로 하는지 찾아보기.
        List<WatchRecord> expiredProcessedWatchRecord = watchRecordRepository.findByAccountIdWhereBoolFalse(findAccount.getId());

        // dto로 목록 내려주기.
        List<WatchRecordResponseDto> watchRecordResponseDtos = new ArrayList<>();

        for (WatchRecord watchRecord : expiredProcessedWatchRecord) {
            // 이 아래서 getContents 할 때 영속성 컨텍스트에 관리됨
            watchRecordResponseDtos.add(new WatchRecordResponseDto(watchRecord.getContents().getId(), watchRecord.getContents().getTitle(),
                    watchRecord.getExpiredAt(), watchRecord.isWatchRecordExpired()));
        }

        return new DataResult(ResponseCode.SUCCESS, watchRecordResponseDtos);
    }

    @Data
    @AllArgsConstructor
    static class WatchRecordResponseDto{
        private Long contentsId;
        private String contentsTitle;
        private LocalDateTime expiredTime;
        private Boolean isExpired;

    }
}
