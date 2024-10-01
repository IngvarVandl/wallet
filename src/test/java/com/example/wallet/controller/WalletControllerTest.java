package com.example.wallet.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletRequest;
import com.example.wallet.service.WalletService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    private WalletRequest walletRequest;
    private UUID walletId;

    @BeforeEach
    public void setUp() {
        walletId = UUID.randomUUID();
        walletRequest = new WalletRequest();
        walletRequest.setWalletId(walletId);
        walletRequest.setOperationType("DEPOSIT");
        walletRequest.setAmount(100L);
    }


    @Test
    public void testUpdateWallet_Success() {
        ResponseEntity<Void> response = walletController.updateWallet(walletRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(walletService).updateBalance(walletId, "DEPOSIT", 100L);
    }

    @Test
    public void testUpdateWallet_BadRequest() {
        doThrow(new IllegalArgumentException("Некорректные данные"))
                .when(walletService).updateBalance(any(UUID.class), anyString(), anyLong());

        ResponseEntity<Void> response = walletController.updateWallet(walletRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(walletService).updateBalance(walletId, "DEPOSIT", 100L);
    }

    @Test
    public void testGetBalance_Success() {
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setBalance(200L); // Задаем баланс

        when(walletService.getWalletById(walletId)).thenReturn(wallet);

        ResponseEntity<Long> response = walletController.getBalance(walletId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200L, response.getBody());
    }

    @Test
    public void testGetBalance_NotFound() {
        when(walletService.getWalletById(walletId)).thenThrow(new EntityNotFoundException("Кошелек не найден"));

        ResponseEntity<Long> response = walletController.getBalance(walletId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
