package com.tenantvaluation.trainning.gateway.domain.port.service;

import com.tenantvaluation.trainning.gateway.domain.user.User;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.CompletableFuture;

public interface UserService extends ReactiveUserDetailsService {

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    CompletableFuture<User> getUserByUsername(String username);

    @Retryable(retryFor = {RestClientException.class}, noRetryFor = {HttpClientErrorException.class},
        maxAttemptsExpression = "${rest-template.maximumRetries}",
        backoff = @Backoff(delayExpression = "${rest-template.fixedBackOffPeriod}"))
    UserDetails loadUserByUsername(String username);
}
