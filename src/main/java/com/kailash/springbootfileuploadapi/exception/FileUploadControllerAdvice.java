package com.kailash.springbootfileuploadapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileUploadControllerAdvice {

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(final StorageFileNotFoundException exc) {
        return ResponseEntity.badRequest().body(exc.getCause().getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleMaxUploadSizeException(final MultipartException e) {
        return ResponseEntity.badRequest().body(e.getCause().getMessage());
    }

    @ExceptionHandler(DuplicateFileNameException.class)
    public ResponseEntity<?> handleDuplicateFileName(final DuplicateFileNameException exc) {
        return ResponseEntity.badRequest().body(exc.getCause().getMessage());
    }
}
