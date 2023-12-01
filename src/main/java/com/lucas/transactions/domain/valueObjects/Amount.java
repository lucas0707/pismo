package com.lucas.transactions.domain.valueObjects;

import java.math.BigDecimal;

public class Amount {
    private final String value;

    public Amount(double value) {
        this.value = new BigDecimal(value * 100).toString();
    }

    public Amount(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public double getValueNumber() {
        return Double.parseDouble(value);
    }
}
