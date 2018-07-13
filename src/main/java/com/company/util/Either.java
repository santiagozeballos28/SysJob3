package com.company.util;

/**
 * @author santiago.mamani
 */
public class Either<E, T> {

    private E errorContainer;
    private T success;

    public Either(E errorContainer, T success) {
        this.errorContainer = errorContainer;
        this.success = success;
    }

    public Either() {
        this.success = null;
        this.errorContainer = null;
    }

    public static <E, T> Either<E, T> success(T success) {
        return new Either<E, T>(null, success);
    }

    public static <E, T> Either<E, T> errorContainer(E errorContainer) {
        return new Either<E, T>(errorContainer, null);
    }

    public boolean success() {
        return success != null;
    }

    public boolean errorContainer() {
        return errorContainer != null;
    }

    public T getSuccess() {
        return success;
    }

    public E getErrorContainer() {
        return errorContainer;
    }

    public void setSuccess(T success) {
        this.success = success;
    }

    public void setErrorContainer(E errorContainer) {
        this.errorContainer = errorContainer;
    }
}
