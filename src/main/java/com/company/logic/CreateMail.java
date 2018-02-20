package com.company.logic;

import com.company.model.Mail;
import com.company.tools.ConstantData;
import com.company.util.Bundle;

/**
 *
 * @author santiago.mamani
 */
public class CreateMail {

    public Mail getInstanceMail(String destination, String subject, String message) {
        Bundle bundle = new Bundle();
        String userMail = bundle.getAuthenticator(ConstantData.MAIL_USER);
        String passwordKey = bundle.getAuthenticator(ConstantData.PASSWORD_KEY_USER);
        return new Mail(userMail, passwordKey, destination, subject, message);
    }
}
