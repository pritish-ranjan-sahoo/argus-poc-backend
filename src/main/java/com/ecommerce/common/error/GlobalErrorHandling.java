package com.ecommerce.common.error;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class GlobalErrorHandling {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorLog> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorLog apiError = new ErrorLog("Access denied: Insufficient permissions", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ErrorLog> handleDuplicateRequestException(DuplicateRequestException ex){
        ErrorLog err = new ErrorLog("User already exists: "+ex.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorLog> handleGenericException(Exception ex) {
        ErrorLog apiError = new ErrorLog("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorLog> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorLog apiError = new ErrorLog(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorLog> handleInsufficientStockException(InsufficientStockException ex) {
        ErrorLog apiError = new ErrorLog(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}