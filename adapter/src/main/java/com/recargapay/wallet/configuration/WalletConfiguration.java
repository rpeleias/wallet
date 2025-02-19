package com.recargapay.wallet.configuration;

import com.recargapay.wallet.port.in.usecase.DepositFundsUseCase;
import com.recargapay.wallet.port.in.usecase.RetrieveBalanceUseCase;
import com.recargapay.wallet.port.in.usecase.RetrieveHistorialBalanceUseCase;
import com.recargapay.wallet.port.in.usecase.TransferFundsUseCase;
import com.recargapay.wallet.port.in.usecase.WalletCreationUseCase;
import com.recargapay.wallet.port.in.usecase.WithdrawFundsUseCase;
import com.recargapay.wallet.port.out.repository.TransactionRepositoryPort;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;
import com.recargapay.wallet.usecase.DepositFundsUseCaseImpl;
import com.recargapay.wallet.usecase.RetrieveBalanceUseCaseImpl;
import com.recargapay.wallet.usecase.RetrieveHistoricalBalanceUseCaseImpl;
import com.recargapay.wallet.usecase.TransferFundsUseCaseImpl;
import com.recargapay.wallet.usecase.WalletCreationUseCaseImpl;
import com.recargapay.wallet.usecase.WithdrawFundsUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletConfiguration {

    @Bean
    public WalletCreationUseCase walletCreationUseCase(WalletRepositoryPort walletRepositoryPort) {
        return new WalletCreationUseCaseImpl(walletRepositoryPort);
    }

    @Bean
    public RetrieveBalanceUseCase retrieveBalanceUseCase(WalletRepositoryPort walletServicePort) {
        return new RetrieveBalanceUseCaseImpl(walletServicePort);
    }

    @Bean
    public RetrieveHistorialBalanceUseCase retrieveHistorialBalanceUseCase(WalletRepositoryPort walletServicePort, TransactionRepositoryPort transactionServicePort) {
        return new RetrieveHistoricalBalanceUseCaseImpl(walletServicePort, transactionServicePort);
    }

    @Bean
    public DepositFundsUseCase depositFundsUseCase(WalletRepositoryPort walletServicePort) {
        return new DepositFundsUseCaseImpl(walletServicePort);
    }

    @Bean
    public WithdrawFundsUseCase withdrawFundsUseCase(WalletRepositoryPort walletServicePort) {
        return new WithdrawFundsUseCaseImpl(walletServicePort);
    }

    @Bean
    public TransferFundsUseCase transferFundsUseCase(WalletRepositoryPort walletRepositoryPort) {
        return new TransferFundsUseCaseImpl(walletRepositoryPort);
    }
}
