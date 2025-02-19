package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.repository.entity.WalletEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WalletMapperTest {

    @Test
    void whenWalletIsInformedThenItShouldBeConvertedToWalletEntity() {
        // Arrange
        Long walletId = 1L;
        Long userId = 100L;
        LocalDateTime creationDate = LocalDateTime.now();
        float amount = 500.0f;

        WalletEntity walletEntity = new WalletEntity(walletId, userId, creationDate, amount, Collections.emptyList());

        Wallet wallet = WalletMapper.from(walletEntity);

        // Assert
        assertNotNull(wallet);
        assertThat(walletId, is(wallet.getId()));
        assertThat(userId, is(wallet.getUserId()));
        assertThat(creationDate, is(wallet.getCreationDate()));
        assertThat(amount, is(wallet.getAmount()));
    }

    @Test
    void whenWalletEntityIsInformedThenItShouldBeConvertedToWallet() {
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