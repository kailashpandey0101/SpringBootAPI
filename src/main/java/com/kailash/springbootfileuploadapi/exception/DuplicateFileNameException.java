package com.kailash.springbootfileuploadapi.exception;

public class DuplicateFileNameException extends RuntimeException {

    public DuplicateFileNameException(String message) {
        super(message);
    }
}
