package service;

import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    private final String SMTP_HOST = "smtp.gmail.com";
    private final String SMTP_PORT = "587"; // TLS port
    private final String SENDER_EMAIL = "myemsbusiness@gmail.com";
    private final String SENDER_PASSWORD = "mkie fyew trvr lfdm";

    public void sendEmail(String recipientEmail, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html");
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);

        } catch (MessagingException e) {
            System.err.println("--- FATAL MAIL SENDING ERROR ---");
            e.printStackTrace();
            System.err.println("---------------------------------");
            throw new RuntimeException("Email failed to send: " + e.getMessage(), e);
        }
    }
}