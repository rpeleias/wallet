package com.recargapay.wallet.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TransactionEntity {

    @Id
    private Long id;
}
