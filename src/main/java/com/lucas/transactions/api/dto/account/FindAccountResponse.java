package com.lucas.transactions.api.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.transactions.domain.account.Account;

import java.util.UUID;

public class FindAccountResponse {
    @JsonProperty("document_number")
    private final String documentNumber;
    @JsonProperty("account_id")
    private final UUID accountId;
    public FindAccountResponse(Account account) {
        this.documentNumber = account.getDocumentNumber();
        this.accountId = account.getId();
    }
}
