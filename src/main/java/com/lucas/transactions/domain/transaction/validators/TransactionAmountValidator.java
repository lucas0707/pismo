package com.lucas.transactions.domain.transaction.validators;

import com.lucas.transactions.domain.valueObjects.Amount;

import java.util.ArrayList;

public abstract class TransactionAmountValidator {
    protected Amount amount;

    public TransactionAmountValidator(Amount amount) {
        this.amount = amount;
    }
    public abstract ArrayList<String> validate();
}
