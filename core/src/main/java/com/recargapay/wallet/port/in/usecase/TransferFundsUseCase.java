package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.TransferFundsCommand;
import com.recargapay.wallet.port.outh.FundsTransfered;

public interface TransferFundsUseCase {

    FundsTransfered transfer(TransferFundsCommand command);
}
