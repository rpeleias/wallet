package com.recargapay.wallet.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(Long transactionId, String walletId, String type, float amount, LocalDateTime timestamp) {
}
