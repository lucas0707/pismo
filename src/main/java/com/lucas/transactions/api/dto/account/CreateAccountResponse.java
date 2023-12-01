package com.lucas.transactions.api.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateAccountResponse {
    @JsonProperty("account_id")
    public UUID accountId;

    public CreateAccountResponse(UUID id) {
        this.accountId = id;
    }
}
