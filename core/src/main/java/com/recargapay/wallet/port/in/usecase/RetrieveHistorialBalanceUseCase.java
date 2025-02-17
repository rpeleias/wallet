package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.RetrieveHistorialBalanceCommand;
import com.recargapay.wallet.port.out.HistorialBalanceRetrieved;

public interface RetrieveHistorialBalanceUseCase {

    HistorialBalanceRetrieved retrieve(RetrieveHistorialBalanceCommand command);
}
