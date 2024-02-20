package com.tenantvaluation.trainning.adapter.config;

import com.tenantvaluation.trainning.adapter.author.AuthorNotFoundException;
import com.tenantvaluation.trainning.adapter.movie.MovieNotFoundException;
import com.tenantvaluation.trainning.adapter.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFoundException(MovieNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.NOT_FOUND, ProblemDetail.forStatus(HttpStatus.NOT_FOUND), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.NOT_FOUND, ProblemDetail.forStatus(HttpStatus.NOT_FOUND), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(AuthorNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.NOT_FOUND, ProblemDetail.forStatus(HttpStatus.NOT_FOUND), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
