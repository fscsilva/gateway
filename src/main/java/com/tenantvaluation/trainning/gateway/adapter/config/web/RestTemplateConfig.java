package com.tenantvaluation.trainning.gateway.adapter.config.web;

import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.v;

@Slf4j
@EnableRetry
@Configuration
public class RestTemplateConfig {

    @Bean
    public List<ClientHttpRequestInterceptor> interceptors() {
        return List.of(this::logRequest, this::logResponse, this::handleNotFound);
    }

    @SneakyThrows(IOException.class)
    protected ClientHttpResponse logRequest(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        log.info("Calling API {} {} with payload {}",
                request.getMethod(),
                request.getURI(),
                v("payload", new String(body, StandardCharsets.UTF_8)));

        return execution.execute(request, body);
    }

    @SneakyThrows(IOException.class)
    protected ClientHttpResponse logResponse(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        var response = execution.execute(request, body);
        log.info("Obtained {} response from API {} {} with payload {}",
                response.getStatusCode(),
                request.getMethod(),
                request.getURI(),
                v("payload", StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8)));
        return response;
    }

    @SneakyThrows(IOException.class)
    protected ClientHttpResponse handleNotFound(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        var response = execution.execute(request, body);
        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return new EmptyResponse(response);
        }
        return response;
    }

    static class EmptyResponse implements ClientHttpResponse {

        @Delegate(excludes = HttpInputMessage.class)
        private final ClientHttpResponse response;

        public EmptyResponse(ClientHttpResponse response) {
            this.response = response;
        }

        @Override
        public InputStream getBody() {
            return null;
        }

        @Override
        public HttpHeaders getHeaders() {
            return response.getHeaders();
        }
    }
}
