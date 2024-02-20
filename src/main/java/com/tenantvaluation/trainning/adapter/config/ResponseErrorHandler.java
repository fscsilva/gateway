package com.tenantvaluation.trainning.adapter.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class ResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return clientHttpResponse.getStatusCode().isError()
                && !clientHttpResponse.getStatusCode().equals(HttpStatus.NOT_FOUND);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        try {
            HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
            if (statusCode == null) {
                byte[] body = this.getResponseBody(response);
                String message = getErrorMessageWithoutTruncate(response.getRawStatusCode(), response.getStatusText(), body, this.getCharset(response));
                throw new UnknownHttpStatusCodeException(message, response.getRawStatusCode(), response.getStatusText(), response.getHeaders(), body, this.getCharset(response));
            } else {
                handleErrorWithoutTruncate(response, statusCode);
            }
        } catch (HttpServerErrorException exception) {
            var message = String
                    .format("Server error calling API %s %s - %s", method, url, exception.getMessage());
            throw new HttpServerErrorException(exception.getStatusCode(), message);

        } catch (HttpClientErrorException exception) {
            var message = String
                    .format("Client error calling API %s %s - %s", method, url, exception.getMessage());
            throw new HttpClientErrorException(exception.getStatusCode(), message);
        }
    }

    private String getErrorMessageWithoutTruncate(int rawStatusCode, String statusText, @Nullable byte[] responseBody, @Nullable Charset charset) {
        String preface = rawStatusCode + " " + statusText + ": ";
        if (ObjectUtils.isEmpty(responseBody)) {
            return preface + "[no body]";
        } else {
            charset = charset == null ? StandardCharsets.UTF_8 : charset;
            return preface + "[" + new String(responseBody, charset) + "]";
        }
    }

    protected void handleErrorWithoutTruncate(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        String statusText = response.getStatusText();
        HttpHeaders headers = response.getHeaders();
        byte[] body = this.getResponseBody(response);
        Charset charset = this.getCharset(response);
        String message = getErrorMessageWithoutTruncate(statusCode.value(), statusText, body, charset);
        switch(statusCode.series()) {
            case CLIENT_ERROR:
                throw HttpClientErrorException.create(message, statusCode, statusText, headers, body, charset);
            case SERVER_ERROR:
                throw HttpServerErrorException.create(message, statusCode, statusText, headers, body, charset);
            default:
                throw new UnknownHttpStatusCodeException(message, statusCode.value(), statusText, headers, body, charset);
        }
    }
}
