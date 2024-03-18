package com.tenantvaluation.trainning.gateway.adapter.config.web;

import com.tenantvaluation.trainning.gateway.adapter.author.AuthorNotFoundException;
import com.tenantvaluation.trainning.gateway.adapter.movie.MovieNotFoundException;
import com.tenantvaluation.trainning.gateway.adapter.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerErrorException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        var errorResponse = new ServerErrorException(ex.getMessage(), ex);
        log.error(ex.getMessage());
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
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponseException(HttpStatus.NOT_FOUND, ProblemDetail.forStatus(HttpStatus.NOT_FOUND), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
