package com.lucas.transactions.infrastructure.postgres;

import com.lucas.transactions.domain.transaction.Transaction;
import com.lucas.transactions.domain.transaction.TransactionRepository;
import com.lucas.transactions.domain.valueObjects.Amount;
import com.transactions.db.tables.records.TransactionsRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.transactions.db.Tables.TRANSACTIONS;

@Repository
public class PostgresqlTransactionRepository implements TransactionRepository {
    private static final Logger logger = LoggerFactory.getLogger(PostgresqlAccountRepository.class);
    private final DSLContext dslContext;

    @Autowired
    public PostgresqlTransactionRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    @Override
    public Transaction create(Transaction transaction) throws Exception {
        logger.info("Adding new transaction for account: {}", transaction.getAccountId());
        TransactionsRecord persisted = dslContext.insertInto(TRANSACTIONS)
                .set(createRecord(transaction))
                .returning()
                .fetchOne();

        if (persisted == null) {
            //@TODO Create custom exception for this.
            throw new Exception("Failed to create transaction.");
        }

        return convertQueryResultToModelObject(persisted);
    }


    private Transaction convertQueryResultToModelObject(TransactionsRecord persisted) {
        return new Transaction(
                persisted.getTransactionId(),
                persisted.getAccountId(),
                persisted.getOperationTypeId(),
                new Amount(persisted.getAmount()),
                persisted.getEventDate()
        );
    }

    private TransactionsRecord createRecord(Transaction transaction) {
        TransactionsRecord transactionsRecord = new TransactionsRecord();

        transactionsRecord.setTransactionId(transaction.getId());
        transactionsRecord.setAccountId(transaction.getAccountId());
        transactionsRecord.setAmount(transaction.getAmount().getValue());
        transactionsRecord.setOperationTypeId(transaction.getOperationType());
        transactionsRecord.setEventDate(transaction.getEventDate());

        return transactionsRecord;
    }
}
