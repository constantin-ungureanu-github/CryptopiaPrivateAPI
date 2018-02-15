package org.cryptopia.api.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TradeHistory {
    @JsonProperty("TradeId")
    private BigInteger tradeId;

    @JsonProperty("TradePairId")
    private BigInteger tradePairId;

    @JsonProperty("Market")
    private String market;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Rate")
    private BigDecimal rate;

    @JsonProperty("Amount")
    private BigDecimal amount;

    @JsonProperty("Total")
    private BigDecimal total;

    @JsonProperty("Fee")
    private BigDecimal fee;

    @JsonProperty("TimeStamp")
    private String timeStamp;
}
