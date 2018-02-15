package org.cryptopia.api.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Transaction {
    @JsonProperty("Id")
    private BigInteger id;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("TxId")
    private String txId;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Amount")
    private BigDecimal amount;

    @JsonProperty("Fee")
    private BigDecimal fee;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Confirmations")
    private BigInteger confirmations;

    @JsonProperty("TimeStamp")
    private String timeStamp;

    @JsonProperty("Address")
    private String address;
}
