package org.cryptopia.api.domain;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SubmitTrade {
    @JsonProperty("OrderId")
    private BigInteger orderId;

    @JsonProperty("FilledOrders")
    private List<BigInteger> filledOrders;
}
