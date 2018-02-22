package com.company.util;

import com.company.tools.ConstantData.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class ErrorContainer {

    private Status status;
    private ArrayList<Error> errors;

    public ErrorContainer() {
        errors = new ArrayList<Error>();
    }
    public ErrorContainer(Status status, Error error) {
        this.status = status;
        errors = new ArrayList<Error>();
        errors.add(error);
    }

    public ErrorContainer(Status status, ArrayList<Error> errors) {
        this.status = status;
        this.errors = errors;
    }
      public Status getStatus() {
        return status;
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setErrors(ArrayList<Error> errors) {
        this.errors = errors;
    }

    public void addError(Error error) {
        errors.add(error);
    }

    public void addAllErrors(ArrayList<Error> errors) {
        this.errors.addAll(errors);
    }

    public void addAllErrors(ErrorContainer errs) {

        this.errors.addAll(errs.getErrors());
    }

    @JsonIgnore
    public boolean hasError() {
        return !errors.isEmpty();
    }
}
