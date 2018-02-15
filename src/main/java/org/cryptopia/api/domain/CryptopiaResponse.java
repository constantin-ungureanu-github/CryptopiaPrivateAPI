package org.cryptopia.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CryptopiaResponse<T> {
    @JsonProperty("Success")
    private String success;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Data")
    private T data;

    @JsonProperty("Error")
    private String error;
}
