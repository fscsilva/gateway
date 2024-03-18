package com.tenantvaluation.trainning.gateway.adapter.author;

import com.tenantvaluation.trainning.gateway.domain.author.Author;
import com.tenantvaluation.trainning.gateway.domain.port.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class AuthorClient implements AuthorService {

    @Qualifier("author-rest")
    private final RestTemplate restTemplate;

    @Value("${author-service.getAuthorByIdUrl}")
    private String getAuthorByIdUrl;

    @Value("${author-service.addAuthorUrl}")
    private String addAuthorUrl;

    @Override
    public CompletableFuture<Author> addAuthor(Author author) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(addAuthorUrl)
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.postForObject(queryUrl, author, Author.class))
            .thenApply(response -> response);
    }

    @Override
    public CompletableFuture<Author> getAuthor(BigInteger id) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(getAuthorByIdUrl)
            .buildAndExpand(id)
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Author.class))
            .thenApply(response -> response);
    }

}