package com.pereverziev;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailMail {

    void sendEmail(String confirmId, String emailTo,String subject,String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dneprtrase@gmail.com", "VovaPolska98");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dneprtrase@gmail.com"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailTo));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (Exception ex) {

        }
    }
}
