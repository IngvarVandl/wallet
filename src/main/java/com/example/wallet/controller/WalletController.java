package com.example.wallet.controller;

import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletRequest;
import com.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<Void> updateWallet(@RequestBody WalletRequest request) {
        try {
            walletService.updateBalance(request.getWalletId(), request.getOperationType(), request.getAmount());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{walletId}")
    public ResponseEntity<Long> getBalance(@PathVariable UUID walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        return ResponseEntity.ok(wallet.getBalance());
    }
}
