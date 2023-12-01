package com.lucas.transactions.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void shouldReturnStringValuePositive() {
        double value = 100.45;
        Amount amount = new Amount(value);
        String expected = "10045";
        Assertions.assertEquals(expected, amount.getValue());
    }

    @Test
    public void shouldReturnNumberValuePositive() {
        double value = 100.45;
        Amount amount = new Amount(value);
        double expected = 10045;
        Assertions.assertEquals(expected, amount.getValueNumber());
    }

    @Test
    public void shouldReturnStringValueNegative() {
        double value = -100.45;
        Amount amount = new Amount(value);
        String expected = "-10045";
        Assertions.assertEquals(expected, amount.getValue());
    }

    @Test
    public void shouldReturnNumberValueNegative() {
        double value = -100.45;
        Amount amount = new Amount(value);
        double expected = -10045;
        Assertions.assertEquals(expected, amount.getValueNumber());
    }

    @Test
    public void shouldReturnStringValueFromString() {
        String value = "10045";
        Amount amount = new Amount(value);
        String expected = "10045";
        Assertions.assertEquals(expected, amount.getValue());
    }
}
