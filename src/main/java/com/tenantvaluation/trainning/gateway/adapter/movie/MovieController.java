package com.tenantvaluation.trainning.gateway.adapter.movie;

import com.tenantvaluation.trainning.gateway.domain.movie.Genre;
import com.tenantvaluation.trainning.gateway.domain.movie.Movie;
import com.tenantvaluation.trainning.gateway.domain.port.api.MovieAPI;
import com.tenantvaluation.trainning.gateway.domain.port.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MovieController implements MovieAPI {

    private final MovieService movieService;

    @Override
    public ResponseEntity<Movie> addMovie(Movie movie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(movie).join());
    }

    @Override
    public ResponseEntity<Movie> updateMovie(BigInteger id, Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie).join());
    }

    @Override
    public ResponseEntity<Void> deleteMovie(BigInteger id) {
        movieService.deleteMovie(Movie.builder().id(id).build());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Movie> getMovie(BigInteger id) {
        return ResponseEntity.ok(movieService.getMovie(id).join());
    }

    @Override
    public ResponseEntity<List<Movie>> getMoviesByAuthorName(String name, int page, int size) {
        return ResponseEntity.ok(movieService.getMoviesByAuthorName(name, page,size).join());
    }

    @Override
    public ResponseEntity<List<Movie>> getMoviesByGenre(BigInteger id, int page, int size) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(Genre.builder().genreId(id).build(), page, size).join());
    }
}
