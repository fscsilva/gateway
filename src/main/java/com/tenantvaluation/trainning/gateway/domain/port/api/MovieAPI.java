package com.tenantvaluation.trainning.gateway.domain.port.api;

import com.tenantvaluation.trainning.gateway.domain.movie.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

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
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Movie> updateMovie(@PathVariable BigInteger id, @RequestBody Movie movie);

    @Operation(summary = "Delete a Movie")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Movie deleted", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to delete a Movie"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteMovie(@PathVariable BigInteger id);

    @Operation(summary = "Get a Movie by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie retrieved", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to get a Movie"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Movie> getMovie(@PathVariable BigInteger id);

    @Operation(summary = "Get a Movies by author")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movies retrieved",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Movie.class))) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to retrieve Movies by author"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<Movie>> getMoviesByAuthorName(@RequestParam String name, @RequestParam("page") int page,
        @RequestParam("size") int size);

    @Operation(summary = "Get a Movies by genre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movies retrieved",
            content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Movie.class))) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to retrieve Movies by genre"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @GetMapping("/genres/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable BigInteger id, @RequestParam("page") int page,
        @RequestParam("size") int size);

}
