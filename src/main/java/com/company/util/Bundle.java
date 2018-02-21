package com.company.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author santiago.mamani
 */
public class Bundle {
    public static String getMessage(String messagePattern, Object[] args) {
        Locale locale = new Locale("en", "US");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/company/properties/BundleMessage", locale);
        MessageFormat messageFormat = new MessageFormat(resourceBundle.getString(messagePattern));
        return messageFormat.format(args);
    }

    public static String getData(String dataPattern) {
        Locale locale = new Locale("en", "US");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/company/properties/BundleData", locale);
        return resourceBundle.getString(dataPattern);
    }
     public static String getAuthenticator(String str) {
         Locale locale = new Locale("en", "US");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/company/properties/BundleAuthenticatorMail", locale);
        return resourceBundle.getString(str);
    }
}
