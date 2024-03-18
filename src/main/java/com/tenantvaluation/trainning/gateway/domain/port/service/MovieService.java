package com.tenantvaluation.trainning.gateway.domain.port.service;

import com.tenantvaluation.trainning.gateway.domain.movie.Genre;
import com.tenantvaluation.trainning.gateway.domain.movie.Movie;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MovieService {

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Movie> addMovie(Movie movie);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Movie> updateMovie(BigInteger id, Movie movie);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    void deleteMovie(Movie movie);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<Movie> getMovie(BigInteger id);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<List<Movie>> getMoviesByGenre(Genre genre, int page, int size);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<List<Movie>> getMoviesByAuthorName(String name, int page, int size);
}
