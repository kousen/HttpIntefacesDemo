package com.kousenit.httpinterfacesdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class ClientAdvice {

    @ExceptionHandler(WebClientResponseException.class)
    public ProblemDetail handleClientException(WebClientResponseException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                ex.getMessage());
    }
}
