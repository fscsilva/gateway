package com.tenantvaluation.trainning.gateway.domain.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tenantvaluation.trainning.gateway.domain.author.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;
import java.time.LocalDate;

@Jacksonized
@Builder
@Getter
public class Movie {

    @JsonProperty
    private final BigInteger id;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final LocalDate year;
    @JsonProperty
    private final Genre genre;
    @JsonProperty
    private final Author author;
    //@JsonProperty
    //private String imageUrl;
    //@JsonProperty
    //private MultipartFile image;

}
