package com.tenantvaluation.trainning.adapter.movie;

import com.tenantvaluation.trainning.domain.movie.Movie;
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
public class MovieClient implements MovieService {

    @Qualifier("movie-rest")
    private final RestTemplate restTemplate;

    @Value("${movie-service.updateMovie}")
    private String updateMovieByIdURL;

    @Value("${movie-service.addMovie}")
    private String addMovieURL;

    @Value("${movie-service.deleteMovie}")
    private String deleteMovieURL;

    @Override
    public CompletableFuture<Movie> addMovie(Movie movie) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(addMovieURL)
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Movie.class))
            .thenApply(response -> response);
    }

    @Override
    public CompletableFuture<Movie> updateMovie(Movie movie) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(updateMovieByIdURL)
            .buildAndExpand(movie.getMovieId())
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Movie.class))
            .thenApply(response -> response);
    }

    @Override
    public CompletableFuture<Movie> deleteMovie(Movie movie) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(deleteMovieURL)
            .buildAndExpand(movie.getMovieId())
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Movie.class))
            .thenApply(response -> response);
    }
}