package com.lucas.transactions.domain.transaction.validators;

import com.lucas.transactions.domain.operationType.OperationType;
import com.lucas.transactions.domain.transaction.validators.CreditValidator;
import com.lucas.transactions.domain.transaction.validators.DebitValidator;
import com.lucas.transactions.domain.transaction.validators.TransactionAmountValidator;
import com.lucas.transactions.domain.valueObjects.Amount;

public class GetTransactionAmountValidatorStrategy {
    private static final int CREDIT_OPERATION = 4;
    public TransactionAmountValidator getValidator(int operationTypeId, Amount amount) {
        if (operationTypeId == CREDIT_OPERATION) {
            return new CreditValidator(amount);
        } else {
            return new DebitValidator(amount);
        }
    }
}
