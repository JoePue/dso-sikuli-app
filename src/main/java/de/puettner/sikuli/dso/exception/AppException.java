package de.puettner.sikuli.dso.exception;

public class AppException extends RuntimeException {

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Exception e) {
        super(e);
    }
}
