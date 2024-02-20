package com.tenantvaluation.trainning.adapter.movie;


import com.tenantvaluation.trainning.adapter.config.ResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MovieClientConfig {

    @Value("${user-service.username}")
    private String username;

    @Value("${user-service.password}")
    private String pwd;

    @Bean("movie-rest")
    public RestTemplate restTemplate(List<ClientHttpRequestInterceptor> interceptors,
                                     @Value("${rest-template.connectTimeout}") long connectTimeout,
                                     @Value("${rest-template.readTimeout}") long readTimeout,
                                     @Value("${movie-service.host}") String hostUrl) {

        HttpHost host = new HttpHost(hostUrl.replace("https://",""), 443);



        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials =
            new UsernamePasswordCredentials(username, pwd.toCharArray());
        provider.setCredentials(new AuthScope(host), credentials);


        CloseableHttpClient client = HttpClientBuilder.create().
            setDefaultCredentialsProvider(provider).useSystemProperties().setDefaultCredentialsProvider(provider).build();
        var factory = new HttpComponentsClientHttpRequestFactory(client);
        factory.setConnectTimeout(Duration.ofMillis(connectTimeout));


        return new RestTemplateBuilder()
            .requestFactory(() -> factory)
                .interceptors(interceptors)
                .additionalInterceptors(this::setHeaders)
                .errorHandler(new ResponseErrorHandler())
                .build();
    }

    @SneakyThrows(IOException.class)
    protected ClientHttpResponse setHeaders(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        var headers = request.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return execution.execute(request, body);
    }
}
