package demo.authservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class MailSendService {

    @Value("${spring.mail.username}")
    private String mailUsername;

    private static String SUBJECT_NAME = "Подтверждение email";
    private static String SENDER_NAME = "Micro-project";
    private static String SENT_TEXT = "Ваш код подтверждения: ";

    private final JavaMailSender mailSender;

    public void send(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setFrom(mailUsername, SENDER_NAME);
            helper.setTo(to);
            helper.setSubject(SUBJECT_NAME);
            helper.setText(SENT_TEXT + code);
            mailSender.send(message);
            log.info("Sent code from email: {}", to);
        } catch (MessagingException | UnsupportedEncodingException | MailException e) {
            throw new RuntimeException(e);
        }
    }

}
