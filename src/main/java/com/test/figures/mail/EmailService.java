package com.test.figures.mail;

import com.test.figures.entity.figures.Figure;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${app.admin.mail}")
    private String email;
    @Value("${app.big-figure.area}")
    private double allowedArea;
    private final JavaMailSender emailSender;
    private final TemplateService templateService;

    @Async
    public void sendMessage(String content) throws MessagingException {

        String subject = "Figure too large alert";
        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom(new InternetAddress(email));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(subject);

        MimeBodyPart body = new MimeBodyPart();
        body.setContent(content, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);
        message.setContent(multipart);

        emailSender.send(message);
    }

    public String messageContent(Figure figure) throws TemplateException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("area", figure.getArea());
        map.put("type", figure.getType());
        map.put("allowed_area", allowedArea);
        if (figure.getVersion() != 0) {
            map.put("modifier", figure.getLastModifiedBy());
            map.put("modified", figure.getLastModifiedDate());
            return templateService.fillTemplate("information_mail_too_large_figure_field_modified", map);
        }
        map.put("author", figure.getCreatedBy());
        map.put("created", figure.getCreatedDate());
        return templateService.fillTemplate("information_mail_too_large_figure_field", map);
    }
}