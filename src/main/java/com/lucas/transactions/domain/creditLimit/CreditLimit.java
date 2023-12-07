package com.lucas.transactions.domain.creditLimit;

import java.util.UUID;

public class CreditLimit {
    private final UUID id;
    private final UUID accountId;
    private final String availableCreditLimit;
    private final String transactionAggregate;

    public CreditLimit(UUID accountId, String availableCreditLimit, String transactionAggregate) {
        this.id = UUID.randomUUID();
        this.accountId = accountId;
        this.availableCreditLimit = availableCreditLimit;
        this.transactionAggregate = transactionAggregate;
    }

    public CreditLimit(UUID id, UUID accountId, String availableCreditLimit, String transactionAggregate) {
        this.id = id;
        this.accountId = accountId;
        this.availableCreditLimit = availableCreditLimit;
        this.transactionAggregate = transactionAggregate;
    }

    public String getTransactionAggregate() {
        return transactionAggregate;
    }

    public String getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public UUID getId() {
        return id;
    }
}
