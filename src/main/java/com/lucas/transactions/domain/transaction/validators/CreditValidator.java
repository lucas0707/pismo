package com.lucas.transactions.domain.transaction.validators;

import com.lucas.transactions.domain.valueObjects.Amount;

import java.util.ArrayList;

public class CreditValidator extends TransactionAmountValidator {
    public CreditValidator(Amount amount) {
        super(amount);
    }

    @Override
    public ArrayList<String> validate() {
        ArrayList<String> errors = new ArrayList<>();
        if (amount.getValueNumber() < 0) {
            errors.add("Credit transactions must have positive amount.");
        }
        return errors;
    }
}
