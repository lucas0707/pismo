package com.lucas.transactions.usecases.account;

import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindAccountUseCase {
    private final AccountRepository accountRepository;

    FindAccountUseCase(@Autowired AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account find(UUID accountId) throws Exception {
        return accountRepository.find(accountId);
    }
}
