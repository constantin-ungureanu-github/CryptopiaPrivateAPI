package org.cryptopia.api.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OpenOrder {
    @JsonProperty("OrderId")
    private BigInteger orderId;

    @JsonProperty("TradePairId")
    private BigInteger tradePairId;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Rate")
    private BigDecimal rate;

    @JsonProperty("Amount")
    private BigDecimal amount;

    @JsonProperty("Total")
    private BigDecimal total;

    @JsonProperty("Remaining")
    private BigDecimal remaining;

    @JsonProperty("TimeStamp")
    private String timeStamp;
}
