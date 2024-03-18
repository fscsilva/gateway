package com.tenantvaluation.trainning.gateway.domain.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Jacksonized
@Builder
@Getter
public class Author {

    @JsonProperty
    private final BigInteger id;
    @JsonProperty
    private final String firstName;
    @JsonProperty
    private final String lastName;
    @JsonProperty
    private final LocalDate birthDate;
    @JsonProperty
    private String birthPlace;

}
