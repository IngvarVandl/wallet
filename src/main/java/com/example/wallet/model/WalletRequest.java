package com.example.wallet.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class WalletRequest {
    @JsonProperty("walletId")
    private UUID walletId;

    @JsonProperty("operationType")
    private String operationType;

    @JsonProperty("amount")
    private double amount;

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public long getAmount() {
        return (long)amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
