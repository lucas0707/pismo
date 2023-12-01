package com.lucas.transactions.domain.valueObjects.transaction.validators;

import com.lucas.transactions.domain.transaction.validators.DebitValidator;
import com.lucas.transactions.domain.transaction.validators.TransactionAmountValidator;
import com.lucas.transactions.domain.valueObjects.Amount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DebitValidatorTests {
    @Test
    void shouldValidateDebitOperation() {
        Amount amount = new Amount(100.00);

        TransactionAmountValidator creditValidator = new DebitValidator(amount);
        ArrayList<String> errors = creditValidator.validate();

        Assertions.assertFalse(errors.isEmpty());
    }

    @Test
    void shouldValidateDebitOperationWhenValueIsNegative() {
        Amount amount = new Amount(-100.00);

        TransactionAmountValidator creditValidator = new DebitValidator(amount);
        ArrayList<String> errors = creditValidator.validate();

        Assertions.assertTrue(errors.isEmpty());
    }
}
