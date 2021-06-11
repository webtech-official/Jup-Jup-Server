package com.gsm.jupjup.service.email;

import com.gsm.jupjup.dto.email.MailDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "s19066@gsm.hs.kr";

    @Transactional
    public MailDto createMailAndChangePassword(String userEmail){
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(userEmail);
        dto.setTitle(userEmail+"님의 JupJup 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. Jupjup 임시비밀번호 안내 관련 이메일 입니다." + "[" + userEmail + "]" +"님의 임시 비밀번호는 "
                + str + " 입니다.");
        updatePassword(str,userEmail);
        return dto;
    }

    @Async
    @Transactional
    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }

    @Transactional
    public void updatePassword(String str,String userEmail){
        String pw = passwordEncoder.encode(str);
        Admin allByEmail = adminRepo.findAllByEmail(userEmail);
        allByEmail.change_password(pw);
    }

    @Transactional
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}