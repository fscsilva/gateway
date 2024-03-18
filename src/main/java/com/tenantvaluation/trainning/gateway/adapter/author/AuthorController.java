package com.tenantvaluation.trainning.gateway.adapter.author;

import com.tenantvaluation.trainning.gateway.domain.author.Author;
import com.tenantvaluation.trainning.gateway.domain.port.api.AuthorAPI;
import com.tenantvaluation.trainning.gateway.domain.port.service.AuthorService;
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
    public ResponseEntity<Author> getAuthor(BigInteger id) {
        return ResponseEntity.ok(authorService.getAuthor(id).join());
    }

}
