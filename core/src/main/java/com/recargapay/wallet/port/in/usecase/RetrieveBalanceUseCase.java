package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.RetrieveBalanceCommand;
import com.recargapay.wallet.port.outh.BalanceRetrieved;

public interface RetrieveBalanceUseCase {

    BalanceRetrieved retrieve(RetrieveBalanceCommand command);
}
