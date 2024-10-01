package com.example.wallet.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    private UUID walletId;
    private Long balance;

    public void Wallet(UUID walletId) {
        this.walletId = walletId;
        this.balance = 0L;
    }
    public Long getBalance() {
        return balance;
    }
    public void setBalance(Long balance) {
        this.balance = balance;
    }
    public UUID getWalletId() {
        return walletId;
    }
    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }
}
