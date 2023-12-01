package com.lucas.transactions.domain.transaction.validators;

import com.lucas.transactions.domain.valueObjects.Amount;

import java.util.ArrayList;
import java.util.List;

public class DebitValidator extends TransactionAmountValidator {
    public DebitValidator(Amount amount) {
        super(amount);
    }

    @Override
    public ArrayList<String> validate() {
        ArrayList<String> errors = new ArrayList<>();
        if (amount.getValueNumber() > 0) {
            errors.add("Debit transactions must have a negative amount.");
        }
        return errors;
    }
}
