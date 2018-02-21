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
        String userMail = Bundle.getAuthenticator(ConstantData.AUTENTICATION_USER_MAIL);
        String passwordKey = Bundle.getAuthenticator(ConstantData.AUTENTICATION_USER_PASSWORD_KEY);
        return new Mail(userMail, passwordKey, destination, subject, message);
    }
}
