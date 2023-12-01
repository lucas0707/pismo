package com.lucas.transactions.domain.transaction;

import com.lucas.transactions.domain.valueObjects.Amount;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final int type;
    private final Amount amount;
    private final UUID accountId;
    private final LocalDateTime eventDate;

    public Transaction(UUID accountId, int type, Amount amount) {
        this.id = UUID.randomUUID();
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.eventDate = LocalDateTime.now();
    }

    public Transaction(UUID id, UUID accountId, int type, Amount amount, LocalDateTime timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.eventDate = timestamp;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public Amount getAmount() {
        return amount;
    }

    public int getOperationType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAccountId() {
        return accountId;
    }
}
