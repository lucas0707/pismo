package com.lucas.transactions.usecases.transaction;

import com.lucas.transactions.api.dto.transaction.CreateTransactionRequest;
import com.lucas.transactions.domain.creditLimit.CreditLimit;
import com.lucas.transactions.domain.creditLimit.CreditLimitRepository;
import com.lucas.transactions.domain.transaction.Transaction;
import com.lucas.transactions.domain.transaction.TransactionRepository;
import com.lucas.transactions.domain.transaction.validators.TransactionCreditLimitValidator;
import com.lucas.transactions.domain.valueObjects.Amount;
import com.lucas.transactions.usecases.transaction.validation.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class CreateTransactionUseCase {
    private final TransactionValidator transactionValidator;
    private final TransactionRepository transactionRepository;

    private final CreditLimitRepository creditLimitRepository;

    @Autowired
    CreateTransactionUseCase(
            TransactionValidator transactionValidator,
            TransactionRepository transactionRepository,
            CreditLimitRepository creditLimitRepository
    ) {
        this.transactionValidator = transactionValidator;
        this.transactionRepository = transactionRepository;
        this.creditLimitRepository = creditLimitRepository;
    }

    @Transactional
    public UUID create(CreateTransactionRequest request) throws Exception {
        transactionValidator.validate(request);
        Amount amount = new Amount(request.amount);
        Transaction transaction = new Transaction(request.accountId, request.operationTypeId, amount);

        //validate credit limit
        CreditLimit creditLimit = creditLimitRepository.fetchByAccountId(request.accountId);

        TransactionCreditLimitValidator validator = new TransactionCreditLimitValidator();
        double newAggregatedValue = transaction.applyAmountToAggregatedValue(
                new Amount(creditLimit.getTransactionAggregate())
        );
        if(!validator.validate(
                transaction,
                newAggregatedValue,
                new Amount(creditLimit.getAvailableCreditLimit())
                )) {
            throw new Exception("Transaction extrapolates credit limit");
        }

        if (transaction.getOperationType() == 4) {
            double oldCreditLimit = new Amount(creditLimit.getAvailableCreditLimit()).getValueNumber();
            double newCreditLimit = oldCreditLimit + transaction.getAmount().getValueNumber();
            creditLimitRepository.updateCreditLimit(new Amount(newCreditLimit), creditLimit.getId());
        } else {
            creditLimitRepository.updateAggregated(new Amount(newAggregatedValue), creditLimit.getId());
        }


        return transactionRepository.create(transaction).getId();
    }
}
