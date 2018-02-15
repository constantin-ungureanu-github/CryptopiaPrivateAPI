package org.cryptopia.api.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DepositAddress {
    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("BaseAddress")
    private String baseAddress;
}
