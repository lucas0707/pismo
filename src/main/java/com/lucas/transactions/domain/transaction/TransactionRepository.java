package com.lucas.transactions.domain.transaction;

import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository {
    Transaction create(Transaction transaction) throws Exception;
}
