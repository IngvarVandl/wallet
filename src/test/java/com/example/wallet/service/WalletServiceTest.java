package com.example.wallet.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private Wallet wallet;
    private UUID walletId;

    @BeforeEach
    public void setUp() {
        walletId = UUID.randomUUID();
        wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setBalance(100L);
    }

    @Test
    public void testUpdateBalance_Deposit() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.updateBalance(walletId, "DEPOSIT", 50L);

        assertEquals(150L, wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    public void testUpdateBalance_Withdraw_Success() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.updateBalance(walletId, "WITHDRAW", 50L);

        assertEquals(50L, wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    public void testUpdateBalance_Withdraw_InsufficientFunds() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            walletService.updateBalance(walletId, "WITHDRAW", 150L);
        });

        assertEquals("Недостаточно средств", exception.getMessage());
        verify(walletRepository, never()).save(wallet);
    }

    @Test
    public void testUpdateBalance_InvalidOperation() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            walletService.updateBalance(walletId, "INVALID_OPERATION", 50L);
        });

        assertEquals("Неправильная операция", exception.getMessage());
        verify(walletRepository, never()).save(wallet);
    }

    @Test
    public void testGetWalletById() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Wallet foundWallet = walletService.getWalletById(walletId);

        assertEquals(wallet, foundWallet);
    }

    @Test
    public void testGetWalletById_NotFound() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            walletService.getWalletById(walletId);
        });

        assertEquals("Кошелек не найден", exception.getMessage());
    }
}
