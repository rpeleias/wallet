package com.recargapay.wallet.repository.service;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.repository.entity.WalletEntity;
import com.recargapay.wallet.repository.impl.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WalletServicePortImplTest {

    @Mock
    private WalletRepository walletRepository;

    private WalletServicePortImpl walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        walletService = new WalletServicePortImpl(walletRepository);
    }

    @Test
    void whenValidUserIsInformedThenWalletIsReturned() {
        Long userId = 1L;
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(1L);
        walletEntity.setUserId(userId);
        when(walletRepository.findByUserId(userId)).thenReturn(Optional.of(walletEntity));

        Optional<Wallet> result = walletService.findByUser(userId);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getId(), is(walletEntity.getId()));
        verify(walletRepository, times(1)).findByUserId(userId);
    }

    @Test
    void whenInvalidUserIsInformedThenWalletIsNotReturned() {
        Long userId = 1L;
        when(walletRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Optional<Wallet> result = walletService.findByUser(userId);

        assertThat(result.isPresent(), is(false));
        verify(walletRepository, times(1)).findByUserId(userId);
    }

    @Test
    void whenNewWalletIsInformedThenItShouldBeSaved() {
        long userId = 1L;
        long walletEntityId = 2L;
        Wallet wallet = Wallet.of(userId);
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUserId(walletEntityId);

        when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);

        Wallet result = walletService.saveOrUpdate(wallet);

        assertThat(result.getId(), is(walletEntity.getId()));
        verify(walletRepository, times(1)).save(any(WalletEntity.class));
    }

    @Test
    void whenWalletIsFoundThenItShouldBeReturned() {
        Long walletId = 1L;
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(walletId);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(walletEntity));

        Optional<Wallet> result = walletService.findById(walletId);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getId(), is(walletId));
        verify(walletRepository, times(1)).findById(walletId);
    }
}