package com.lucas.transactions.api.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccountRequest {
    @JsonProperty("document_number")
    public String documentNumber;
}
