package org.cryptopia.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.cryptopia.api.domain.Balance;
import org.cryptopia.api.domain.DepositAddress;
import org.cryptopia.api.domain.OpenOrder;
import org.cryptopia.api.domain.SubmitTrade;
import org.cryptopia.api.domain.TradeHistory;
import org.cryptopia.api.domain.Transaction;

import lombok.NonNull;

public interface CryptopiaPrivateAPI {
    static final String REST_URI = "https://www.cryptopia.co.nz/api/";

    List<Balance> getBalance();

    List<Balance> getBalance(@NonNull Integer currencyId);

    List<Balance> getBalance(@NonNull String currencyName);

    Optional<DepositAddress> getDepositAddress(@NonNull Integer currencyId);

    Optional<DepositAddress> getDepositAddress(@NonNull String currencyName);

    List<OpenOrder> getOpenOrders();

    List<OpenOrder> getOpenOrders(@NonNull String market);

    List<OpenOrder> getOpenOrders(@NonNull String market, @NonNull Integer count);

    List<OpenOrder> getOpenOrders(@NonNull Integer tradePairId);

    List<OpenOrder> getOpenOrders(@NonNull Integer tradePairId, @NonNull Integer count);

    List<TradeHistory> getTradeHistory();

    List<TradeHistory> getTradeHistory(@NonNull String market);

    List<TradeHistory> getTradeHistory(@NonNull String market, @NonNull Integer count);

    List<TradeHistory> getTradeHistory(@NonNull Integer tradePairId);

    List<TradeHistory> getTradeHistory(@NonNull Integer tradePairId, @NonNull Integer count);

    List<Transaction> getTransactions(@NonNull String type);

    List<Transaction> getTransactions(@NonNull String type, @NonNull Integer count);

    Optional<SubmitTrade> submitTrade(@NonNull String market, @NonNull String type, @NonNull BigDecimal rate, @NonNull BigDecimal amount);

    Optional<SubmitTrade> submitTrade(@NonNull Integer tradePairId, @NonNull String type, @NonNull BigDecimal rate, @NonNull BigDecimal amount);

    List<Long> cancelAllTrades();

    List<Long> cancelTradesByOrderId(@NonNull BigInteger orderId);

    List<Long> cancelTradesByTradePairId(@NonNull Integer tradePairId);
}
