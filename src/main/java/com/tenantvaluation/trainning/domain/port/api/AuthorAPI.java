package com.tenantvaluation.trainning.domain.port.api;

import com.tenantvaluation.trainning.domain.author.Author;
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
@RequestMapping("v1/authors")
public interface AuthorAPI {


    @Operation(summary = "Creates a new Author")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Author created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to create a Author"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Author> addAuthor(@RequestBody Author author);

    @Operation(summary = "Update a Author")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Author updated", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to update a Author"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PutMapping("/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody Author author);

    @Operation(summary = "Delete a Author")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Author deleted", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to delete a Author"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId);

}
