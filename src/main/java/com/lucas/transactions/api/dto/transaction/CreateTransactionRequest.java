package com.lucas.transactions.api.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateTransactionRequest {
    @JsonProperty("account_id")
    public UUID accountId;
    public double amount;
    @JsonProperty("operation_type_id")
    public int operationTypeId;
}
