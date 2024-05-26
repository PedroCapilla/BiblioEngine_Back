package org.scimat_plus.bibliometricwe.customermanager.exception;

import org.springframework.http.HttpStatus;

public class CustomerErrorException extends RuntimeException{

    private HttpStatus httpStatus;

    public CustomerErrorException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
