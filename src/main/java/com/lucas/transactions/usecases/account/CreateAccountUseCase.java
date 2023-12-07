package com.lucas.transactions.usecases.account;

import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;
import com.lucas.transactions.usecases.creditLimit.CreateCreditLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateAccountUseCase {
    private final AccountRepository accountRepository;

    private final CreateCreditLimit createAccountCreditLimit;


    @Autowired
    CreateAccountUseCase(
            AccountRepository accountRepository,
            CreateCreditLimit createAccountCreditLimit
    ) {
        this.accountRepository = accountRepository;
        this.createAccountCreditLimit = createAccountCreditLimit;
    }

    public UUID createAccount(CreateAccountRequest request) throws Exception {
        //@TODO add validation for account creation
        // verify document number and if there is any account with that document number

        Account newAccount = new Account(request.documentNumber);

        UUID accountId = accountRepository.create(newAccount).getId();

        createAccountCreditLimit.createForAccount(accountId);
        return accountId;
    }
}
