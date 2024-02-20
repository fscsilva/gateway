package com.tenantvaluation.trainning.domain.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tenantvaluation.trainning.domain.author.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;
import java.time.Year;

@Jacksonized
@Builder
@Getter
public class Movie {

    @JsonProperty
    private final BigInteger movieId;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final Year password;
    @JsonProperty
    private final Genre genre;
    @JsonProperty
    private final Author author;
    @JsonProperty
    private String imageUrl;

}
