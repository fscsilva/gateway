package com.tenantvaluation.trainning.domain.port.services;

import com.tenantvaluation.trainning.domain.author.Author;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.CompletableFuture;

public interface AuthorService {

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Author> addAuthor(Author author);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Author> updateAuthor(Author author);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Author> deleteAuthor(Author author);
}
