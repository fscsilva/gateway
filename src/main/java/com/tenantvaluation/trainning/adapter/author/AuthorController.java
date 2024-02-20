package com.tenantvaluation.trainning.adapter.author;

import com.tenantvaluation.trainning.domain.author.Author;
import com.tenantvaluation.trainning.domain.movie.Movie;
import com.tenantvaluation.trainning.domain.port.api.AuthorAPI;
import com.tenantvaluation.trainning.domain.port.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RequiredArgsConstructor
@RestController
public class AuthorController implements AuthorAPI {

    private final AuthorService authorService;

    @Override
    public ResponseEntity<Author> addAuthor(Author author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.addAuthor(author).join());
    }

    @Override
    public ResponseEntity<Author> updateAuthor(Long authorId, Author author) {
        return ResponseEntity.ok(authorService.updateAuthor(author).join());
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(Long authorId) {
        authorService.deleteAuthor(Author.builder().authorId(BigInteger.valueOf(authorId)).build()).join();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
