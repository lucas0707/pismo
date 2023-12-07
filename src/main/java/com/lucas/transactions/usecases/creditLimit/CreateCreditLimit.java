package com.lucas.transactions.usecases.creditLimit;

import com.lucas.transactions.domain.creditLimit.CreditLimit;
import com.lucas.transactions.domain.creditLimit.CreditLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCreditLimit {
    private final CreditLimitRepository creditLimitRepository;


    @Autowired
    CreateCreditLimit(CreditLimitRepository creditLimitRepository) {
        this.creditLimitRepository = creditLimitRepository;
    }

    public UUID createForAccount(UUID accountId) throws Exception {
        CreditLimit creditLimit = new CreditLimit(accountId, "50000", "0");
        return creditLimitRepository.create(creditLimit).getId();
    }
}
