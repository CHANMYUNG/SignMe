package com.nooheat.manager;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by NooHeat on 15/10/2017.
 */
public class Mail {
    public static boolean sendEmail(String email, String verifyCode) throws MessagingException {
        final String username = System.getenv("SIGNME_EMAIL_ID");
        final String password = System.getenv("SIGNME_EMAIL_PASSWORD");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("AccountManager@signme.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
        message.setSubject("SIGNME 이메일 인증코드");

        message.setText("다음 코드를 입력해주세요."
                + verifyCode);

        Transport.send(message);

        System.out.println("Done");

        return true;
    }

}
