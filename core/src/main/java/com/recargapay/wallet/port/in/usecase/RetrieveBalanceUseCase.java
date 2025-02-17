package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.RetrieveBalanceCommand;
import com.recargapay.wallet.port.out.BalanceRetrieved;

public interface RetrieveBalanceUseCase {

    BalanceRetrieved retrieve(RetrieveBalanceCommand command);
}
