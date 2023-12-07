package com.lucas.transactions.domain.transaction.validators;

import com.lucas.transactions.domain.transaction.Transaction;
import com.lucas.transactions.domain.valueObjects.Amount;

public class TransactionCreditLimitValidator {
    public boolean validate(Transaction transaction, double newAggregated, Amount creditLimit) {
        if (transaction.getOperationType() == 4) {
            return true;
        }
        if (newAggregated > creditLimit.getValueNumber()) {
            return false;
        }
        return true;
    }
}
