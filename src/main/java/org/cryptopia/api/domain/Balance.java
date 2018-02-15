package org.cryptopia.api.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Balance {
    @JsonProperty("CurrencyId")
    private BigInteger currencyId;

    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("Total")
    private BigDecimal total;

    @JsonProperty("Available")
    private BigDecimal id;

    @JsonProperty("Unconfirmed")
    private BigDecimal unconfirmed;

    @JsonProperty("HeldForTrades")
    private BigDecimal heldForTrades;

    @JsonProperty("PendingWithdraw")
    private BigDecimal pendingWithdraw;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("BaseAddress")
    private String baseAddress;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("StatusMessage")
    private String statusMessage;
}
