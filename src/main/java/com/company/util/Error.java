package com.company.util;

/**
 *
 * @author santiago.mamani
 */
public class Error {

    private Integer keyError;
    private String message;

    public Error() {
    }

    public Error(Integer keyError, String message) {
        this.keyError = keyError;
        this.message = message;
    }

    /**
     * @return the keyError
     */
    public Integer getKeyError() {
        return keyError;
    }

    /**
     * @param keyError the keyError to set
     */
    public void setKeyError(Integer keyError) {
        this.keyError = keyError;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
