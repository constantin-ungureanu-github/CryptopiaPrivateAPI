package org.cryptopia.api.application;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.cryptopia.api.CryptopiaPrivateAPI;
import org.cryptopia.api.domain.Balance;
import org.cryptopia.api.domain.CryptopiaResponse;
import org.cryptopia.api.domain.DepositAddress;
import org.cryptopia.api.domain.OpenOrder;
import org.cryptopia.api.domain.SubmitTrade;
import org.cryptopia.api.domain.TradeHistory;
import org.cryptopia.api.domain.Transaction;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptopiaPrivateImplementation implements CryptopiaPrivateAPI {
    private final Client client = ClientBuilder.newClient();
    private final WebTarget target = client.target(REST_URI);
    private final String privateKey;
    private final String publicKey;

    public CryptopiaPrivateImplementation(@NonNull final String privateKey, @NonNull final String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    private static String getNonce() {
        return Long.toString(System.currentTimeMillis());
    }

    private static String MD5_Base64(@NonNull final String postParameter) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest(postParameter.getBytes("UTF-8")));
    }

    private static String SHA256_Base64(@NonNull final String privateKey, @NonNull final String message)
            throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
        final Mac HMAC_SHA256 = Mac.getInstance("HmacSHA256");
        final SecretKeySpec secret_key = new SecretKeySpec(Base64.getDecoder().decode(privateKey), "HmacSHA256");
        HMAC_SHA256.init(secret_key);
        return Base64.getEncoder().encodeToString(HMAC_SHA256.doFinal(message.getBytes("UTF-8")));
    }

    private static String getRequestSignature(@NonNull final String publicKey, final String nonce, final String endpoint, final String jSonPostParam)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return publicKey + "POST" + URLEncoder.encode(REST_URI + endpoint, StandardCharsets.UTF_8.toString()).toLowerCase() + nonce + MD5_Base64(jSonPostParam);
    }

    private String getAuthorization(@NonNull final String publicKey, @NonNull final String privateKey, @NonNull final String endpoint,
            @NonNull final String jsonPostParam) {
        String nonce = "";
        String sha256Base64 = "";
        try {
            nonce = getNonce();
            sha256Base64 = SHA256_Base64(privateKey, getRequestSignature(publicKey, nonce, endpoint, jsonPostParam));
        } catch (final InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | UnsupportedEncodingException e) {
            log.error("{}", e.getMessage(), e);
        }

        return "amx " + publicKey + ":" + sha256Base64 + ":" + nonce;
    }

    @Override
    public List<Balance> getBalance() {
        return getBalanceHelper("{}");
    }

    @Override
    public List<Balance> getBalance(@NonNull final Integer currencyId) {
        return getBalanceHelper("{\"CurrencyId\":" + currencyId + "}");
    }

    @Override
    public List<Balance> getBalance(@NonNull final String currencyName) {
        return getBalanceHelper("{\"Currency\":\"" + currencyName + "\"}");
    }

    private List<Balance> getBalanceHelper(@NonNull final String jsonPostParam) {
        final String endpoint = "GetBalance";
        log.info("{}{}{}", REST_URI, endpoint, jsonPostParam);

        final String authorization = getAuthorization(publicKey, privateKey, endpoint, jsonPostParam);
        final CryptopiaResponse<List<Balance>> response = target.path(endpoint).request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                .get(new GenericType<CryptopiaResponse<List<Balance>>>() {});

        if (Boolean.parseBoolean(response.getSuccess())) {
            return response.getData();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<DepositAddress> getDepositAddress(@NonNull final Integer currencyId) {
        return getDepositAddressHelper("{\"CurrencyId\":" + currencyId + "}");
    }

    @Override
    public Optional<DepositAddress> getDepositAddress(@NonNull final String currencyName) {
        return getDepositAddressHelper("{\"Currency\":\"" + currencyName + "\"}");
    }

    private Optional<DepositAddress> getDepositAddressHelper(@NonNull final String jsonPostParam) {
        final String endpoint = "GetDepositAddress";
        log.info("{}{}{}", REST_URI, endpoint, jsonPostParam);

        final String authorization = getAuthorization(publicKey, privateKey, endpoint, jsonPostParam);
        final CryptopiaResponse<DepositAddress> response = target.path(endpoint).request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                .get(new GenericType<CryptopiaResponse<DepositAddress>>() {});

        if (Boolean.parseBoolean(response.getSuccess())) {
            return Optional.ofNullable(response.getData());
        }

        return Optional.empty();
    }

    @Override
    public List<OpenOrder> getOpenOrders() {
        return getOpenOrdersHelper("{}");
    }

    @Override
    public List<OpenOrder> getOpenOrders(@NonNull final String market) {
        return getOpenOrdersHelper("{\"Market\":\"" + market + "\"}");
    }

    @Override
    public List<OpenOrder> getOpenOrders(@NonNull final String market, @NonNull final Integer count) {
        return getOpenOrdersHelper("{\"Market\":\"" + market + "\", \"Count\":" + count + "}");
    }

    @Override
    public List<OpenOrder> getOpenOrders(@NonNull final Integer tradePairId) {
        return getOpenOrdersHelper("{\"TradePairId\":" + tradePairId + "}");
    }

    @Override
    public List<OpenOrder> getOpenOrders(@NonNull final Integer tradePairId, @NonNull final Integer count) {
        return getOpenOrdersHelper("{\"TradePairId\":" + tradePairId + ", \"Count\":" + count + "}");
    }

    private List<OpenOrder> getOpenOrdersHelper(@NonNull final String jsonPostParam) {
        final String endpoint = "GetOpenOrders";
        log.info("{}{}{}", REST_URI, endpoint, jsonPostParam);

        final String authorization = getAuthorization(publicKey, privateKey, endpoint, jsonPostParam);
        final CryptopiaResponse<List<OpenOrder>> response = target.path(endpoint).request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                .get(new GenericType<CryptopiaResponse<List<OpenOrder>>>() {});

        if (Boolean.parseBoolean(response.getSuccess())) {
            return response.getData();
        }

        return Collections.emptyList();
    }

    @Override
    public List<TradeHistory> getTradeHistory() {
        return getTradeHistoryHelper("{}");
    }

    @Override
    public List<TradeHistory> getTradeHistory(@NonNull final String market) {
        return getTradeHistoryHelper("{\"Market\":\"" + market + "\"}");
    }

    @Override
    public List<TradeHistory> getTradeHistory(@NonNull final String market, @NonNull final Integer count) {
        return getTradeHistoryHelper("{\"Market\":\"" + market + "\", \"Count\":" + count + "}");
    }

    @Override
    public List<TradeHistory> getTradeHistory(@NonNull final Integer tradePairId) {
        return getTradeHistoryHelper("{\"TradePairId\":" + tradePairId + "}");
    }

    @Override
    public List<TradeHistory> getTradeHistory(@NonNull final Integer tradePairId, @NonNull final Integer count) {
        return getTradeHistoryHelper("{\"TradePairId\":" + tradePairId + ", \"Count\":" + count + "}");
    }

    private List<TradeHistory> getTradeHistoryHelper(@NonNull final String jsonPostParam) {
        final String endpoint = "GetTradeHistory";
        log.info("{}{}{}", REST_URI, endpoint, jsonPostParam);

        final String authorization = getAuthorization(publicKey, privateKey, endpoint, jsonPostParam);
        final CryptopiaResponse<List<TradeHistory>> response = target.path(endpoint).request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                .get(new GenericType<CryptopiaResponse<List<TradeHistory>>>() {});

        if (Boolean.parseBoolean(response.getSuccess())) {
            return response.getData();
        }

        return Collections.emptyList();
    }

    @Override
    public List<Transaction> getTransactions(@NonNull final String type) {
        return getTransactionsHelper("{\"Type\":\"" + type + "\"}");
    }

    @Override
    public List<Transaction> getTransactions(@NonNull final String type, @NonNull final Integer count) {
        return getTransactionsHelper("{\"Type\":\"" + type + "\", \"Count\":" + count + "}");
    }

    private List<Transaction> getTransactionsHelper(@NonNull final String jsonPostParam) {
        final String endpoint = "GetTransactions";
        log.info("{}{}{}", REST_URI, endpoint, jsonPostParam);

        final String authorization = getAuthorization(publicKey, privateKey, endpoint, jsonPostParam);
        final CryptopiaResponse<List<Transaction>> response = target.path(endpoint).request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                .get(new GenericType<CryptopiaResponse<List<Transaction>>>() {});

        if (Boolean.parseBoolean(response.getSuccess())) {
            return response.getData();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<SubmitTrade> submitTrade(@NonNull final String market, @NonNull final String type, @NonNull final BigDecimal rate,
            @NonNull final BigDecimal amount) {
        return submitTradeHelper("{\"Market\":\"" + market + "\", \"Type\":\"" + type + "\", \"Rate\":" + rate + ",\"Amount\":" + amount + "}");
    }

    @Override
    public Optional<SubmitTrade> submitTrade(@NonNull final Integer tradePairId, @NonNull final String type, @NonNull final BigDecimal rate,
            @NonNull final BigDecimal amount) {
        return submitTradeHelper("{\"TradePairId\":" + tradePairId + ", \"Type\":\"" + type + "\", \"Rate\":" + rate + ",\"Amount\":" + amount + "}");
    }

    private Optional<SubmitTrade> submitTradeHelper(@NonNull final String jsonPostParam) {
        final String endpoint = "SubmitTrade";
        log.info("{}{}{}", REST_URI, endpoint, jsonPostParam);

        final String authorization = getAuthorization(publicKey, privateKey, endpoint, jsonPostParam);
        final CryptopiaResponse<SubmitTrade> response = target.path(endpoint).request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                .get(new GenericType<CryptopiaResponse<SubmitTrade>>() {});

        if (Boolean.parseBoolean(response.getSuccess())) {
            return Optional.ofNullable(response.getData());
        }

        return Optional.empty();
    }

    @Override
    public List<Long> cancelAllTrades() {
        return cancelTradeHelper("{\"Type\":\"All\"}");
    }

    @Override
    public List<Long> cancelTradesByOrderId(@NonNull final BigInteger orderId) {
        return cancelTradeHelper("{\"Type\":\"Trade\",\"OrderId\":" + orderId + "}");
    }

    @Override
    public List<Long> cancelTradesByTradePairId(@NonNull final Integer tradePairId) {
        return cancelTradeHelper("{\"Type\":\"TradePair\",\"TradePairId\":" + tradePairId + "}");
    }

    private List<Long> cancelTradeHelper(@NonNull final String jsonPostParam) {
        final String endpoint = "CancelTrade";
        log.info("{}{}{}", REST_URI, endpoint, jsonPostParam);

        final String authorization = getAuthorization(publicKey, privateKey, endpoint, jsonPostParam);
        final CryptopiaResponse<List<Long>> response = target.path(endpoint).request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                .get(new GenericType<CryptopiaResponse<List<Long>>>() {});

        if (Boolean.parseBoolean(response.getSuccess())) {
            return response.getData();
        }

        return Collections.emptyList();
    }
}
