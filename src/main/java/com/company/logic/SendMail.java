package com.company.logic;

import com.company.model.Mail;
import com.company.tools.ConstantData.Status;
import com.company.tools.ConstantKeyError;
import com.company.util.Either;
import com.company.util.ErrorContainer;
import com.company.util.Error;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author santiago.mamani
 */
public class SendMail {

    public SendMail() {
    }

    public Either<ErrorContainer, Boolean> sendMail(Mail mail) {
        final String userMail = mail.getUserMail();
        final String passwordKey = mail.getPasswordKey();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userMail, passwordKey);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userMail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getDestination()));
            message.setSubject(mail.getSubject());
            message.setText(mail.getMessage());
            Transport.send(message);
            return Either.success(true);
        } catch (Exception e) {
            return Either.errorContainer(new ErrorContainer(Status.INTERNAL_SERVER_ERROR, new Error(ConstantKeyError.SERVER_EMAIL, e.getMessage())));
        }
    }
}
