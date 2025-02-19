package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.RetrieveHistorialBalanceCommand;
import com.recargapay.wallet.port.in.usecase.RetrieveHistorialBalanceUseCase;
import com.recargapay.wallet.port.outh.HistorialBalanceRetrieved;
import com.recargapay.wallet.port.outh.repository.TransactionRepositoryPort;
import com.recargapay.wallet.port.outh.repository.WalletRepositoryPort;

import java.util.List;

public class RetrieveHistoricalBalanceUseCaseImpl implements RetrieveHistorialBalanceUseCase {

    private final WalletRepositoryPort walletRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public RetrieveHistoricalBalanceUseCaseImpl(WalletRepositoryPort walletRepositoryPort, TransactionRepositoryPort transactionRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    public HistorialBalanceRetrieved retrieve(RetrieveHistorialBalanceCommand command) {
        Wallet wallet = walletRepositoryPort.findById(command.getWalletId()).orElseThrow(() -> new WalletNotFoundException(command.getWalletId()));
        List<Transaction> transactions = transactionRepositoryPort.findByWalletIdBetweenDates(wallet.getId(), command.getFrom(), command.getTo());
        wallet.calculateHistoricalAmount(transactions);

        return HistorialBalanceRetrieved.from(wallet.getId(), wallet.getAmount(), transactions);
    }
}
