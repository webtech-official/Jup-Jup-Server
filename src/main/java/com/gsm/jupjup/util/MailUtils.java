package com.gsm.jupjup.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@RequiredArgsConstructor
public class MailUtils {

    private final JavaMailSender mailSender;
    private final MimeMessage message;
    private final MimeMessageHelper messageHelper;

    public MailUtils(JavaMailSender mailSender) throws MessagingException {
        this.mailSender = mailSender;
        message = this.mailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    public void setText(String htmlContent) throws MessagingException {
        messageHelper.setText(htmlContent, true);
    }

    public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
        messageHelper.setFrom(email, name);
    }

    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    public void addInline(String contentId, DataSource dataSource) throws MessagingException {
        messageHelper.addInline(contentId, dataSource);
    }

    public void send() {
        mailSender.send(message);
    }

    //인증 키 크기 지정 변수
    private int size;

    //인증 키 생성
    public String getKey(int size){
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
}
