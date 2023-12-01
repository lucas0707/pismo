package com.lucas.transactions.usecases.account;

import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Component
public class CreateAccountUseCase implements CreateAccountService {
    private final AccountRepository accountRepository;

    CreateAccountUseCase(@Autowired AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createAccount(CreateAccountRequest request) throws Exception {
        //@TODO add validation for account creation
        // verify document number and if there is any account with that document number

        Account newAccount = new Account(request.documentNumber);

        return accountRepository.create(newAccount).getId();
    }
}
