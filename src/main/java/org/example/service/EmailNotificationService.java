package org.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailNotificationService {
    private final JavaMailSender mailSender;
    private final String from;

    public EmailNotificationService(JavaMailSender mailSender,
                                    @Value("${email.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void sendCode(String to, String code) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Ваш OTP-код");
            helper.setText("Ваш код подтверждения: " + code);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Не удалось сформировать Email-сообщение", e);
        }
    }
}
