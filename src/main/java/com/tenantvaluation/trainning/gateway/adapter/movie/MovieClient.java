package com.tenantvaluation.trainning.gateway.adapter.movie;

import com.tenantvaluation.trainning.gateway.domain.movie.Genre;
import com.tenantvaluation.trainning.gateway.domain.movie.Movie;
import com.tenantvaluation.trainning.gateway.domain.port.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class MovieClient implements MovieService {

    @Qualifier("movie-rest")
    private final RestTemplate restTemplate;

    @Value("${movie-service.updateMovieUrl}")
    private String updateMovieByIdURL;

    @Value("${movie-service.addMovieUrl}")
    private String addMovieURL;

    @Value("${movie-service.deleteMovieUrl}")
    private String deleteMovieURL;
    @Value("${movie-service.getMovieByIdURL}")
    private String getMovieByIdURL;
    @Value("${movie-service.getMoviesByGenreURL}")
    private String getMoviesByGenreURL;
    @Value("${movie-service.getMoviesByAuthorNameURL}")
    private String getMoviesByAuthorNameURL;

    @Override
    public CompletableFuture<Movie> addMovie(Movie movie) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(addMovieURL)
            .toUriString();

        return CompletableFuture.supplyAsync(() -> restTemplate.postForObject(queryUrl, movie, Movie.class));

    }

    @Override
    public CompletableFuture<Movie> updateMovie(BigInteger id, Movie movie) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(updateMovieByIdURL)
            .buildAndExpand(id)
            .toUriString();

        return CompletableFuture.supplyAsync(() -> restTemplate.patchForObject(queryUrl, movie, Movie.class));
    }

    @Override
    public void deleteMovie(Movie movie) {

        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(deleteMovieURL)
            .buildAndExpand(movie.getId())
            .toUriString();

        restTemplate.delete(queryUrl, movie);
    }

    @Override
    public CompletableFuture<Movie> getMovie(BigInteger id) {
        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(getMovieByIdURL)
            .buildAndExpand(id)
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Movie.class));
    }

    @Override
    public CompletableFuture<List<Movie>> getMoviesByGenre(Genre genre, int page, int size) {
        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(getMoviesByGenreURL)
            .buildAndExpand(genre.getGenreId(), page, size)
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Movie[].class))
            .thenApply(response -> Arrays.stream(response).toList());
    }

    @Override
    public CompletableFuture<List<Movie>> getMoviesByAuthorName(String name, int page, int size) {
        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(getMoviesByAuthorNameURL)
            .buildAndExpand(name,page,size)
            .toUriString();

        return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, Movie[].class))
            .thenApply(response -> Arrays.stream(response).toList());
    }
}