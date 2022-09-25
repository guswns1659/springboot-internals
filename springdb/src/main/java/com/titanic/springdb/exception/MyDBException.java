package com.titanic.springdb.exception;

public class MyDBException extends RuntimeException {
    public MyDBException(Throwable cause) {
        super(cause);
    }
}
