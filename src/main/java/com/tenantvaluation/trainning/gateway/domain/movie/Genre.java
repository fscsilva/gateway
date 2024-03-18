package com.tenantvaluation.trainning.gateway.domain.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

@Jacksonized
@Builder
@Getter
public class Genre {

    @JsonProperty
    private final BigInteger genreId;
    @JsonProperty
    private final String description;

}
