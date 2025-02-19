package com.recargapay.wallet.web.controller;

import com.recargapay.wallet.port.in.command.DepositFundsCommand;
import com.recargapay.wallet.port.in.command.TransferFundsCommand;
import com.recargapay.wallet.port.in.command.WalletCreationCommand;
import com.recargapay.wallet.port.in.command.WithdrawFundsCommand;
import com.recargapay.wallet.port.out.BalanceRetrieved;
import com.recargapay.wallet.port.out.CreatedWallet;
import com.recargapay.wallet.port.out.FundsDeposited;
import com.recargapay.wallet.port.out.FundsTransfered;
import com.recargapay.wallet.port.out.FundsWithdrawed;
import com.recargapay.wallet.port.out.HistorialBalanceRetrieved;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface WalletControllerDocs {

    @Operation(summary = "Create a new wallet", description = "Creates a new wallet for a user.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Wallet created successfully", content = @Content(schema = @Schema(implementation = CreatedWallet.class))), @ApiResponse(responseCode = "404", description = "User informed not valid")})
    ResponseEntity<CreatedWallet> createWallet(WalletCreationCommand command);

    @Operation(summary = "Retrieve wallet balance", description = "Retrieves the current balance of a wallet.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Balance retrieved successfully", content = @Content(schema = @Schema(implementation = BalanceRetrieved.class))), @ApiResponse(responseCode = "404", description = "Wallet not found")})
    ResponseEntity<BalanceRetrieved> retrieveBalance(Long walletId);

    @Operation(summary = "Retrieve wallet balance history", description = "Retrieves the balance history of a wallet within a date range.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Balance history retrieved successfully", content = @Content(schema = @Schema(implementation = HistorialBalanceRetrieved.class))), @ApiResponse(responseCode = "400", description = "Invalid date range"), @ApiResponse(responseCode = "404", description = "Wallet not found")})
    ResponseEntity<HistorialBalanceRetrieved> retrieveHistorialBalance(Long walletId, LocalDateTime from, LocalDateTime to);

    @Operation(summary = "Deposit funds", description = "Deposits funds into a wallet.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Funds deposited successfully", content = @Content(schema = @Schema(implementation = FundsDeposited.class))), @ApiResponse(responseCode = "400", description = "Invalid input data")})
    ResponseEntity<FundsDeposited> deposit(DepositFundsCommand command);

    @Operation(summary = "Withdraw funds", description = "Withdraws funds from a wallet.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Funds withdrawn successfully", content = @Content(schema = @Schema(implementation = FundsWithdrawed.class))), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "404", description = "Wallet not found")})
    ResponseEntity<FundsWithdrawed> withdrawFunds(WithdrawFundsCommand command);

    @Operation(summary = "Transfer funds", description = "Transfers funds from one wallet to another.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Funds transferred successfully", content = @Content(schema = @Schema(implementation = FundsTransfered.class))), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "404", description = "Wallet not found")})
    ResponseEntity<FundsTransfered> transferFunds(TransferFundsCommand command);
}