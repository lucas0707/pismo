package com.lucas.transactions.domain.account;

import java.util.UUID;

public class Account {
    private final String documentNumber;
    private final UUID id;

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
        this.id = UUID.randomUUID();
    }

    public Account(String accountNumber, UUID id) {
        this.documentNumber = accountNumber;
        this.id = id;
    }


    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public UUID getId() {
        return this.id;
    }
}
