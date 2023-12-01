package com.lucas.transactions.domain.operationType;

public class OperationType {
    private final int id;
    private final String description;

    OperationType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
