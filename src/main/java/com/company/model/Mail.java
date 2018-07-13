package com.company.model;

/**
 *
 * @author santiago.mamani
 */
public class Mail {

    private String userMail;
    private String passwordKey;
    private String destination;
    private String subject;
    private String message;

    public Mail() {
    }

    public Mail(String userMail, String passwordKey, String destination, String subject, String message) {
        this.userMail = userMail;
        this.passwordKey = passwordKey;
        this.destination = destination;
        this.subject = subject;
        this.message = message;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public String getDestination() {
        return destination;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
