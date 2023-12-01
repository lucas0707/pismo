package com.lucas.transactions.domain.valueObjects.transaction.validators;

import com.lucas.transactions.domain.transaction.validators.CreditValidator;
import com.lucas.transactions.domain.transaction.validators.DebitValidator;
import com.lucas.transactions.domain.transaction.validators.GetTransactionAmountValidatorStrategy;
import com.lucas.transactions.domain.transaction.validators.TransactionAmountValidator;
import com.lucas.transactions.domain.valueObjects.Amount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetTransactionAmountValidatorStrategyTests {

    @Test
    void shouldReturnCorrectValidator() {
        GetTransactionAmountValidatorStrategy strategy = new GetTransactionAmountValidatorStrategy();
        Amount amount = new Amount(100.00);
        int creditOperationId = 4;
        int debitOperationId = 2;

        TransactionAmountValidator creditValidator = strategy.getValidator(creditOperationId, amount);
        Assertions.assertEquals(CreditValidator.class, creditValidator.getClass());

        TransactionAmountValidator debitValidator = strategy.getValidator(debitOperationId, amount);
        Assertions.assertEquals(DebitValidator.class, debitValidator.getClass());
    }
}
