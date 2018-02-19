package com.company.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author santiago.mamani
 */
public class Bundle {

    private Locale locale;

    public Bundle() {
        locale = new Locale("en", "US");
    }

    public String getMessage(String messagePattern, Object[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/company/properties/BundleMessage", locale);
        MessageFormat messageFormat = new MessageFormat(resourceBundle.getString(messagePattern));
        return messageFormat.format(args);
    }

    public String getData(String dataPattern) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/company/properties/BundleData", locale);
        return resourceBundle.getString(dataPattern);
    }
}
