package com.tenantvaluation.trainning.gateway.domain.port.service;

import com.tenantvaluation.trainning.gateway.domain.author.Author;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public interface AuthorService {

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Author> addAuthor(Author author);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Author> getAuthor(BigInteger id);
}
