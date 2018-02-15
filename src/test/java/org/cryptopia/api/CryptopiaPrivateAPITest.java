package org.cryptopia.api;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.cryptopia.api.application.CryptopiaPrivateImplementation;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptopiaPrivateAPITest {
    private static final int CURRENCY_ID = 1;
    private static final String CURRENCY_NAME = "BTC";
    private static final String MARKET = "BTC/USDT";
    private static final int TRADEPAIR_ID = 5760;
    private static final int COUNT = 10;

    // Replace with actual keys
    private final CryptopiaPrivateAPI cryptopia = new CryptopiaPrivateImplementation(null, null);

    @Test
    public void testGetGetBalance() {
        cryptopia.getBalance().stream().forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetGetBalanceCurrency() {
        cryptopia.getBalance(CURRENCY_NAME).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetGetBalanceCurrencyId() {
        cryptopia.getBalance(CURRENCY_ID).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetDepositAddressCurrency() {
        cryptopia.getDepositAddress(CURRENCY_NAME).ifPresent(result -> log.info("{}", result));
    }

    @Test
    public void testGetDepositAddressCurrencyId() {
        cryptopia.getDepositAddress(CURRENCY_ID).ifPresent(result -> log.info("{}", result));
    }

    @Test
    public void testGetOpenOrders() {
        cryptopia.getOpenOrders().forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetOpenOrdersMarket() {
        cryptopia.getOpenOrders(MARKET).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetOpenOrdersMarketCount() {
        cryptopia.getOpenOrders(MARKET, COUNT).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetOpenOrdersTradePairIdCount() {
        cryptopia.getOpenOrders(TRADEPAIR_ID, COUNT).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetOpenOrdersTradePairId() {
        cryptopia.getOpenOrders(TRADEPAIR_ID).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetTradeHistory() {
        cryptopia.getTradeHistory().forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetTradeHistoryMarket() {
        cryptopia.getTradeHistory(MARKET).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetGetTradeHistoryMarketCount() {
        cryptopia.getTradeHistory(MARKET, COUNT).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetTradeHistoryTradePairIdCount() {
        cryptopia.getTradeHistory(TRADEPAIR_ID, COUNT).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetTradeHistoryTradePairId() {
        cryptopia.getTradeHistory(TRADEPAIR_ID).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetTransactionHistoryMarketCount() {
        cryptopia.getTransactions("Deposit", COUNT).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testGetTransactionHistoryMarket() {
        cryptopia.getTransactions("Withdraw").forEach(result -> log.info("{}", result));
    }

    @Test
    public void testSubmitTradeTradePairId() {
        final BigDecimal rate = BigDecimal.valueOf(1000.0);
        final BigDecimal amount = BigDecimal.valueOf(1);

        cryptopia.submitTrade(MARKET, "Sell", rate, amount).ifPresent(result -> log.info("{}", result));
    }

    @Test
    public void testSubmitTradeMarket() {
        final BigDecimal rate = BigDecimal.valueOf(1000.0);
        final BigDecimal amount = BigDecimal.valueOf(1);

        cryptopia.submitTrade(TRADEPAIR_ID, "Sell", rate, amount).ifPresent(result -> log.info("{}", result));
    }

    @Test
    public void testCancelAllTrades() {
        cryptopia.cancelAllTrades().forEach(result -> log.info("{}", result));
    }

    @Test
    public void testCancelTradeByOrderId() {
        final BigInteger orderId = BigInteger.valueOf(285045097);
        cryptopia.cancelTradesByOrderId(orderId).forEach(result -> log.info("{}", result));
    }

    @Test
    public void testCancelTradeByTradePairId() {
        cryptopia.cancelTradesByTradePairId(TRADEPAIR_ID).forEach(result -> log.info("{}", result));
    }
}
