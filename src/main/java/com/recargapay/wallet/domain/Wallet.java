package com.recargapay.wallet.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public record Wallet(Long id, Long userId, String currency, LocalDateTime creationDate, float amount) {
    public static Wallet of(Long userId, String currency) {
        Optional<Long> safeId = Optional.ofNullable(null);
        return new Wallet(safeId.orElse(0L), userId, currency, LocalDateTime.now(), 0);
    }
}
