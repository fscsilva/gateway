package com.tenantvaluation.trainning.adapter.author;

import com.tenantvaluation.trainning.domain.author.Author;
import com.tenantvaluation.trainning.domain.movie.Movie;
import com.tenantvaluation.trainning.domain.port.services.AuthorService;
import com.tenantvaluation.trainning.domain.port.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class AuthorClient implements AuthorService {

    @Qualifier("author-rest")
    private final RestTemplate restTemplate;

    @Value("${author-service.updateAuthor}")
    private String updateAuthorURL;

    @Value("${author-service.addAuthor}")
    private String addAuthorURL;

    @Value("${author-service.deleteAuthor}")
    private String deleteAuthorURL;

    @Override
    public CompletableFuture<Author> addAuthor(Author author) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(addAuthorURL)
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Author.class))
            .thenApply(response -> response);
    }

    @Override
    public CompletableFuture<Author> updateAuthor(Author author) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(updateAuthorURL)
            .buildAndExpand(author.getAuthorId())
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Author.class))
            .thenApply(response -> response);
    }

    @Override
    public CompletableFuture<Author> deleteAuthor(Author author) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(deleteAuthorURL)
            .buildAndExpand(author.getAuthorId())
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Author.class))
            .thenApply(response -> response);
    }
}