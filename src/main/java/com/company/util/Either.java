package com.company.util;

/**
 * @author santiago.mamani
 */
public class Either<Error, T> {

    private Error error;
    private T success;

    public Either(Error error, T success) {
        this.error = error;
        this.success = success;
    }

    public Either() {
        this.success = null;
        this.error = null;
    }

    public static <Error, T> Either<Error, T> success(T success) {
        return new Either<Error, T>(null, success);
    }

    public static <Error, T> Either<Error, T> error(Error error) {
        return new Either<Error, T>(error, null);
    }

    public boolean success() {
        return success != null;
    }

    public boolean error() {
        return error != null;
    }

    public T getSuccess() {
        return success;
    }

    public Error getError() {
        return error;
    }

    public void setSuccess(T success) {
        this.success = success;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
