package com.tenantvaluation.trainning.domain.port.api;

import com.tenantvaluation.trainning.domain.movie.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/movies")
public interface MovieAPI {


    @Operation(summary = "Creates a new Movie")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Movie created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to create a Movie"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Movie> addMovie(@RequestBody Movie movie);

    @Operation(summary = "Update a Movie")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie updated", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to update a Movie"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PutMapping("/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Movie> updateMovie(@PathVariable Long movieId, @RequestBody Movie movie);

    @Operation(summary = "Delete a Movie")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Movie deleted", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to delete a Movie"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteMovie(@PathVariable Long movieId);

}
