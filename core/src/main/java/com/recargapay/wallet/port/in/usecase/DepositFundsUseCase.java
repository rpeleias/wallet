package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.DepositFundsCommand;
import com.recargapay.wallet.port.out.FundsDeposited;

public interface DepositFundsUseCase {

    FundsDeposited deposit(DepositFundsCommand command);
}
