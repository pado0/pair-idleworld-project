package com.pado.idleworld.watchrecord;

import com.pado.idleworld.account.AccountRepository;
import com.pado.idleworld.common.CommonResult;
import com.pado.idleworld.common.DataResult;
import com.pado.idleworld.common.ResponseCode;
import com.pado.idleworld.contents.ContentsRepository;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.domain.Contents;
import com.pado.idleworld.domain.WatchRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WatchRecordController {

    private final WatchRecordRepository watchRecordRepository;
    private final AccountRepository accountRepository;
    private final ContentsRepository contentsRepository;

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

        // 아직 메모리에 밖에 없으므로 메모리에 watchRecord 를 세팅해준다.
        findAccount.getWatchRecords().add(watchRecord);
        watchRecord.setAccount(findAccount);

        return new CommonResult(ResponseCode.SUCCESS);
    }

    // 이쪽 쿼리만 잘짜면 된다!!
    @GetMapping("/v1/watch-record")
    public String recordGet(Principal principal) {
        Long count = watchRecordRepository.findByAccountId(accountRepository.findByEmail(principal.getName()).getId());
        return count.toString();
    }

}
