package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public void updateBalance(UUID walletId, String operationType, Long amount){
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Кошелек не найден"));

        switch (operationType) {
        case "DEPOSIT":
            wallet.setBalance(wallet.getBalance() + amount);
            break;
        case "WITHDRAW":
            if (wallet.getBalance() < amount) {
                throw new IllegalArgumentException("Недостаточно средств");
            }
            wallet.setBalance(wallet.getBalance() - amount);
            break;
        default:
            throw new IllegalArgumentException("Неправильная операция");
    }
        walletRepository.save(wallet);
    }
    public Wallet getWalletById(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Кошелек не найден"));
    }
}
