package com.lucas.transactions.usecases.account;

import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateAccountUseCase {
    private final AccountRepository accountRepository;

    CreateAccountUseCase(@Autowired AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UUID createAccount(CreateAccountRequest request) throws Exception {
        Account newAccount = new Account(request.documentNumber);

        return accountRepository.create(newAccount).getId();
    }
}
