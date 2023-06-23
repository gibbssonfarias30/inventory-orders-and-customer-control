package com.backfcdev.managementsystem.exception.handler;



import com.backfcdev.managementsystem.exception.MediaFileNotFoundException;
import com.backfcdev.managementsystem.exception.ModelNotFoundException;
import com.backfcdev.managementsystem.exception.StorageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;



@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ModelNotFoundException.class)
    ProblemDetail handleModelNotFoundException(ModelNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(StorageException.class)
    ProblemDetail handleStorageException(StorageException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, ex.getMessage());
        problemDetail.setTitle("Expectation Failed");
        problemDetail.setType(URI.create("/expectation-failed"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MediaFileNotFoundException.class)
    ProblemDetail handleMediaFileNotFoundException(MediaFileNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
