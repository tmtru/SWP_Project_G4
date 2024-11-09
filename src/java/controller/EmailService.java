package controller;

import java.io.UnsupportedEncodingException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

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
            try {
                message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
            }
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageContent, "text/html; charset=UTF-8"); // Set content type and encoding
            

            Multipart multipart = new javax.mail.internet.MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
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
        String to = "tambiet2411@gmail.com";
        String subject = "Hello: \n world";
        String message = "Hello: \n world";
        IJavaMail emailService = new EmailService();
        sv.send(to, subject, message);
    }
    
}
