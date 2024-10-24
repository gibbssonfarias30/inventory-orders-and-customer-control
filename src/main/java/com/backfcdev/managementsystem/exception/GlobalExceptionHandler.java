package com.backfcdev.managementsystem.exception;



import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(ModelNotFoundException.class)
    ProblemDetail handleModelNotFoundException(ModelNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("/not-found"));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(StorageException.class)
    ProblemDetail handleStorageException(StorageException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(EXPECTATION_FAILED, ex.getMessage());
        problemDetail.setTitle("Expectation Failed");
        problemDetail.setType(URI.create("/expectation-failed"));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MediaFileNotFoundException.class)
    ProblemDetail handleMediaFileNotFoundException(MediaFileNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("/not-found"));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(InsufficientStockException.class)
    ProblemDetail handleInsufficientStockException(InsufficientStockException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflict");
        problemDetail.setDetail("There is not enough stock for this product");
        problemDetail.setType(URI.create("/conflict"));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }
}
