package com.tenantvaluation.trainning.adapter.movie;

import com.tenantvaluation.trainning.domain.movie.Movie;
import com.tenantvaluation.trainning.domain.port.api.MovieAPI;
import com.tenantvaluation.trainning.domain.port.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RequiredArgsConstructor
@RestController
public class MovieController implements MovieAPI {

    private final MovieService movieService;

    @Override
    public ResponseEntity<Movie> addMovie(Movie movie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(movie).join());
    }

    @Override
    public ResponseEntity<Movie> updateMovie(Long movieId, Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(movie).join());
    }

    @Override
    public ResponseEntity<Void> deleteMovie(Long movieId) {
        movieService.deleteMovie(Movie.builder().movieId(BigInteger.valueOf(movieId)).build()).join();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
