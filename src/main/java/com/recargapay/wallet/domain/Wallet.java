package com.recargapay.wallet.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Wallet(Long id, Long userId, String currency, LocalDateTime creationDate, BigDecimal amount) {
}
