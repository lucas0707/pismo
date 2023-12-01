package com.lucas.transactions.usecases.transaction;

import com.lucas.transactions.api.dto.transaction.CreateTransactionRequest;
import com.lucas.transactions.domain.transaction.Transaction;
import com.lucas.transactions.domain.transaction.TransactionRepository;
import com.lucas.transactions.domain.valueObjects.Amount;
import com.lucas.transactions.usecases.transaction.validation.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Component
public class CreateTransactionUseCase {
    private final TransactionValidator transactionValidator;
    private final TransactionRepository transactionRepository;

    @Autowired
    CreateTransactionUseCase(TransactionValidator transactionValidator, TransactionRepository transactionRepository) {
        this.transactionValidator = transactionValidator;
        this.transactionRepository = transactionRepository;
    }
    public UUID create(CreateTransactionRequest request) throws Exception {
        transactionValidator.validate(request);
        Amount amount = new Amount(request.amount);
        Transaction transaction = new Transaction(request.accountId, request.operationTypeId, amount);

        return transactionRepository.create(transaction).getId();
    }
}
