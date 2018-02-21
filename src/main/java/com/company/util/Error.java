package com.company.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author santiago.mamani
 */
public class Error extends ObjectModel {

    private Status status;
    private ArrayList<String> errors;

    public Error(String error) {
        errors = new ArrayList<String>();
        errors.add(error);
    }

    public Error(Status status, ArrayList<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public Error() {
        errors = new ArrayList<String>();
    }

    public Status getStatus() {
        return status;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public void addAllErrors(Error error) {
        errors.addAll(error.getErrors());
    }

    @JsonIgnore
    public boolean isEmpty() {
        return errors.isEmpty();
    }
}
