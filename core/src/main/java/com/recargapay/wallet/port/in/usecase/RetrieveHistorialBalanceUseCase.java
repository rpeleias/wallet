package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.RetrieveHistorialBalanceCommand;
import com.recargapay.wallet.port.outh.HistorialBalanceRetrieved;

public interface RetrieveHistorialBalanceUseCase {

    HistorialBalanceRetrieved retrieve(RetrieveHistorialBalanceCommand command);
}
