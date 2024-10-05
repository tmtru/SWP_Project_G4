package controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService implements IJavaMail {

    @Override
    public boolean send(String to, String subject, String messageContent) {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Bật TLS
        props.put("mail.smtp.host", EmailProperty.HOST_NAME);
        props.put("mail.smtp.port", EmailProperty.TSL_PORT); // Sử dụng cổng TLS

        // get Session
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailProperty.APP_EMAIL, EmailProperty.APP_PASSWORD);
            }
        });
        
        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageContent);

            // send message
            Transport.send(message);

            System.out.println("Message sent successfully");
            return true; // Return true to indicate success
        } catch (MessagingException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            return false; // Return false to indicate failure
        }
    }

    public static void main(String[] args) {
        EmailService sv = new EmailService();
        String to = "toanthhe181060@fpt.edu.vn";
        String subject = "Hello world";
        String message = "hi";
        IJavaMail emailService = new EmailService();
        emailService.send(to, subject, message);
    }
    
}
