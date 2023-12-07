package com.lucas.transactions.domain.creditLimit;

import com.lucas.transactions.domain.valueObjects.Amount;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditLimitRepository {
    CreditLimit fetchByAccountId(UUID accountId) throws Exception;
    CreditLimit create(CreditLimit creditLimit) throws Exception;

    void updateAggregated(Amount newAggregatedValue, UUID id);
}
