package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.repository.entity.WalletEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WalletMapperTest {

    @Test
    void testFromWalletEntityToWallet() {
        // Arrange
        Long walletId = 1L;
        Long userId = 100L;
        LocalDateTime creationDate = LocalDateTime.now();
        float amount = 500.0f;

        WalletEntity walletEntity = new WalletEntity(walletId, userId, creationDate, amount, Collections.emptyList());

        Wallet wallet = WalletMapper.from(walletEntity);

        // Assert
        assertNotNull(wallet);
        assertThat(walletId).isEqualTo(wallet.getId());
        assertThat(userId).isEqualTo(wallet.getUserId());
        assertThat(creationDate).isEqualTo(wallet.getCreationDate());
        assertThat(amount).isEqualTo(wallet.getAmount());
    }

    @Test
    void testFromWalletToWalletEntity() {
        // Arrange
        Long walletId = 1L;
        Long userId = 100L;
        LocalDateTime creationDate = LocalDateTime.now();
        float amount = 500.0f;
        Wallet wallet = Wallet.of(walletId, userId, creationDate, amount);

        // Act
        WalletEntity walletEntity = WalletMapper.from(wallet);

        // Assert
        assertNotNull(walletEntity);
        assertEquals(walletId, walletEntity.getId());
        assertEquals(amount, walletEntity.getAmount());
    }
}