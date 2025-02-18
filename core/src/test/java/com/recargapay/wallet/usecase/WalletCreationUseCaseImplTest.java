package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletAlreadyExistsException;
import com.recargapay.wallet.port.in.command.WalletCreationCommand;
import com.recargapay.wallet.port.out.CreatedWallet;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WalletCreationUseCaseImplTest {

    private final long NEW_USER_ID = 123L;
    private final long EXISTING_USER_ID = 321L;

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    private WalletCreationUseCaseImpl walletCreationUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        walletCreationUseCase = new WalletCreationUseCaseImpl(walletRepositoryPort);
    }

    @Test
    void whenNewUserIsInformedThenNewWalletIsCreated() {
        WalletCreationCommand command = new WalletCreationCommand(NEW_USER_ID);
        Wallet savedWallet = Wallet.of(NEW_USER_ID);

        // when
        when(walletRepositoryPort.findByUser(NEW_USER_ID)).thenReturn(Optional.empty());
        when(walletRepositoryPort.saveOrUpdate(any(Wallet.class))).thenReturn(savedWallet);

        CreatedWallet createdWallet = walletCreationUseCase.create(command);

        // then
        assertThat(createdWallet, is(notNullValue()));
        assertThat(createdWallet.getUserId(), is(NEW_USER_ID));

        verify(walletRepositoryPort, times(1)).findByUser(NEW_USER_ID);
        verify(walletRepositoryPort, times(1)).saveOrUpdate(any(Wallet.class));
    }

    @Test
    void whenExistingUserIsInformedThenWalletIsNotCreated() {
        WalletCreationCommand command = new WalletCreationCommand(EXISTING_USER_ID);
        Wallet existingWallet = Wallet.of(EXISTING_USER_ID);

        //when
        when(walletRepositoryPort.findByUser(EXISTING_USER_ID)).thenReturn(Optional.of(existingWallet));

        // then
        WalletAlreadyExistsException exception = assertThrows(WalletAlreadyExistsException.class, () -> walletCreationUseCase.create(command));
        assertThat(exception.getMessage(), is("Wallet with userId 321 already exists!"));

        verify(walletRepositoryPort, times(1)).findByUser(EXISTING_USER_ID);
        verify(walletRepositoryPort, never()).saveOrUpdate(any(Wallet.class));
    }
}