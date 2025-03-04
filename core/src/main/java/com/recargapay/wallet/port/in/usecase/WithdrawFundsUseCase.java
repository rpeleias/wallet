package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.WithdrawFundsCommand;
import com.recargapay.wallet.port.outh.FundsWithdrawed;

public interface WithdrawFundsUseCase {

    FundsWithdrawed withdraw(WithdrawFundsCommand command);
}
