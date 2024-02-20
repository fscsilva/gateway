package com.tenantvaluation.trainning.domain.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tenantvaluation.trainning.domain.movie.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Year;

@Jacksonized
@Builder
@Getter
public class Author {

    @JsonProperty
    private final BigInteger authorId;
    @JsonProperty
    private final String firstName;
    @JsonProperty
    private final String lastName;
    @JsonProperty
    private final LocalDateTime birthDate;
    @JsonProperty
    private String birthPlace;

}
