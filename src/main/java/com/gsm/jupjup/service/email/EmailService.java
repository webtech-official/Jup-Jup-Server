package com.gsm.jupjup.service.email;

import com.gsm.jupjup.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@RequiredArgsConstructor
@Service("mss")
public class EmailService {

    private final JavaMailSenderImpl mailSender;
    //인증 키 크기 지정 변수
    private int size;

    //인증 키 생성
    private String getKey(int size){
        this.size = size;
        return getAuthCode();
    }

    //인증코드 난수 발생.
    private String getAuthCode(){
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;
        while(buffer.length() < size){
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    //인증 메일 보내기.
    public String sendAuthMail(String email){
        //6자리 난수 인증번호 생성.
        String authKey = getKey(6);

        //인증메일 보내기.
        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("JubJub 회원인증 이메일 입니다.");
            sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
                    .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
                    .append("<a href='http://localhost:8080/v2/member/signUpConfirm?email=")
                    .append(email)
                    .append("&AuthKey=")
                    .append(authKey)
                    .append("' target='_blenk'>이메일 인증 확인</a>")
                    .toString());
            sendMail.setFrom("webtechnologiesofficial@gmail.com", "JubJub-Official");
            sendMail.setTo(email);
            sendMail.send();
        }catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return authKey;
    }
}
