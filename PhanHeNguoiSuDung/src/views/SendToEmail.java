package views;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendToEmail {

	SendToEmail(String destmail, String mess) {

        final String username = "congchuanhuy19@gmail.com";
        final String password = "zuqcmthcfnxogicw";

        Properties prop = new Properties();
        prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("congchuanhuy19@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destmail));
            
            message.setSubject("NEW PASSWORD");
            message.setText(mess);
            
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    

}
