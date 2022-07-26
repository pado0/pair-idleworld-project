package com.pado.idleworld.account;

import com.pado.idleworld.account.mail.MailDTO;
import com.pado.idleworld.domain.Account;
import com.pado.idleworld.domain.AccountRole;
import com.pado.idleworld.exception.AccountNotExistException;
import com.pado.idleworld.exception.DuplicationElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JavaMailSender mailSender;

    @Transactional
    public void accountUpdate(String email, AccountUpdateRequest request) {
        Account findAccount = accountRepository.findByEmail(email);
        String encPw = passwordEncoding(request.getPassword());
        findAccount.setPassword(encPw);
        findAccount.setImageUrl(request.getImageUrl());
        findAccount.setNickname(request.getNickname());
    }

    @Transactional
    public void accountCreate(SignUpForm request) {
        duplicationCheck(request.getEmail());

        String encPw = passwordEncoding(request.getPassword());
        request.setPassword(encPw);

        Account account = Account.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .imageUrl(request.getImageUrl())
                .agree(request.isAgree())
                .role(AccountRole.ROLE_USER) //기본생성 USER로
                .provider(request.getProvider())
                .providerId(request.getProviderId())
                .build();

        accountRepository.save(account);
    }

    public void duplicationCheck(String email) {
        if(accountRepository.existsByEmail(email)) {
            throw new DuplicationElementException();
        }
    }

    public String passwordEncoding(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public AccountInfoResponse accountRead(String email) {
        Account findAccount = accountRepository.findByEmail(email);
        return AccountInfoResponse.builder()
                .email(findAccount.getEmail())
                .nickname(findAccount.getNickname())
                .imageUrl(findAccount.getImageUrl())
                .agree(findAccount.isAgree())
                //.playListId(findAccount.getPlayList().getId())
                .build();
    }

    @Transactional
    public MailDTO createMailAndChangePassword(String memberEmail) {
        checkEmail(memberEmail);

        String tempStr = getTempPassword();
        String str = passwordEncoding(tempStr);

        MailDTO dto = new MailDTO();
        dto.setAddress(memberEmail);
        dto.setTitle("아이들나라 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. 아이들나라 임시비밀번호 안내 관련 이메일입니다."
                + " 회원님의 임시 비밀번호는 " + tempStr + "입니다."
        + " 로그인 후에 비밀번호를 변경해 주세요.");
        updatePassword(str, memberEmail);
        return dto;
    }

    private void checkEmail(String memberEmail) {
        if(!accountRepository.existsByEmail(memberEmail)) {
            throw new AccountNotExistException();
        }
    }

    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    @Transactional
    public void updatePassword(String str, String userEmail) {
        String memberPassword = str;
        Account findAccount = accountRepository.findByEmail(userEmail);
        findAccount.setPassword(str);
        findAccount.setFailCount(0);
    }

    public void mailSend(MailDTO mailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getAddress());
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());
        message.setFrom("idleworld8080@naver.com");
        message.setReplyTo("idleworld8080@naver.com");
        System.out.println("message"+message);
        mailSender.send(message);
    }

    @Transactional
    public void roleAdmin(String email) {
        checkEmail(email);
        Account findAccount = accountRepository.findByEmail(email);
        findAccount.setRole(AccountRole.ROLE_ADMIN);
    }

    @Transactional
    public void roleUser(String email) {
        checkEmail(email);
        Account findAccount = accountRepository.findByEmail(email);
        findAccount.setRole(AccountRole.ROLE_USER);
    }

}
