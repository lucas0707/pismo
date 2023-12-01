package com.lucas.transactions.usecases.account;

import com.lucas.transactions.api.dto.account.CreateAccountRequest;

import java.util.UUID;

public interface CreateAccountService {
    UUID createAccount(CreateAccountRequest request) throws Exception;
}
