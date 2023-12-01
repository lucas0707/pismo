package com.lucas.transactions.usecases.transaction.validation;

import com.lucas.transactions.api.dto.transaction.CreateTransactionRequest;
import com.lucas.transactions.domain.account.AccountRepository;
import com.lucas.transactions.domain.operationType.OperationTypeRepository;
import com.lucas.transactions.domain.transaction.validators.GetTransactionAmountValidatorStrategy;
import com.lucas.transactions.domain.valueObjects.Amount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TransactionValidator {
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Autowired
    TransactionValidator(AccountRepository accountRepository, OperationTypeRepository operationTypeRepository) {
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
    }

    public void validate(CreateTransactionRequest request) throws Exception {
        ArrayList<String> errors = new ArrayList<>();
        if (!accountRepository.any(request.accountId)) {
            errors.add("Invalid account.");
        }
        if (!operationTypeRepository.hasOperationType(request.operationTypeId)) {
            errors.add("Invalid operation type.");
        }

        //@TODO duplicating errors here, take a look later
        ArrayList<String> totalErrors = validateAmount(request.operationTypeId, request.amount, errors);
        if (!totalErrors.isEmpty()) {
            throw new Exception("Invalid transaction." + totalErrors.toString());
        }
    }

    private ArrayList<String> validateAmount(int operationTypeId, double amount, ArrayList<String> errors) {
        ArrayList<String> amountErrors = new GetTransactionAmountValidatorStrategy()
                .getValidator(operationTypeId, new Amount(amount)).validate();

        if (!amountErrors.isEmpty()) {
            errors.addAll(amountErrors);
        }
        return errors;
    }
}
