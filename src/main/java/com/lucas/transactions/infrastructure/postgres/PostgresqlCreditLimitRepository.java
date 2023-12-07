package com.lucas.transactions.infrastructure.postgres;

import com.lucas.transactions.domain.creditLimit.CreditLimit;
import com.lucas.transactions.domain.creditLimit.CreditLimitRepository;
import com.lucas.transactions.domain.valueObjects.Amount;
import com.transactions.db.tables.records.AccountCreditLimitRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.transactions.db.Tables.ACCOUNT_CREDIT_LIMIT;

@Repository
public class PostgresqlCreditLimitRepository implements CreditLimitRepository {
    private static final Logger logger = LoggerFactory.getLogger(PostgresqlCreditLimitRepository.class);
    private final DSLContext dslContext;

    @Autowired
    public PostgresqlCreditLimitRepository(
            DSLContext dslContext
    ) {
        this.dslContext = dslContext;
    }

    @Transactional
    @Override
    public CreditLimit fetchByAccountId(UUID accountId) throws Exception {
        logger.info("Looking for account credit limit with account id: {}", accountId);

        AccountCreditLimitRecord queryResults = dslContext.selectFrom(ACCOUNT_CREDIT_LIMIT)
                .where(ACCOUNT_CREDIT_LIMIT.ACCOUNT_ID.equal(accountId))
                .fetchOne();

        if (queryResults == null) {
            //@TODO Create custom exception for this.
            throw new Exception("No credit limit found account id: " + accountId);
        }

        return convertQueryResultToModelObject(queryResults);
    }

    @Transactional
    @Override
    public CreditLimit create(CreditLimit creditLimit) throws Exception {
        logger.info("Adding new account credit limit for account: {}", creditLimit.getAccountId());
        AccountCreditLimitRecord persisted = dslContext.insertInto(ACCOUNT_CREDIT_LIMIT)
                .set(createRecord(creditLimit))
                .returning()
                .fetchOne();

        if (persisted == null) {
            //@TODO Create custom exception for this.
            throw new Exception("Failed to create account limit.");
        }

        return convertQueryResultToModelObject(persisted);
    }

    @Override
    public void updateAggregated(Amount newAggregatedValue, UUID id) {
        logger.info("Updating aggregated value for credit limit: {}", id);

        dslContext.update(ACCOUNT_CREDIT_LIMIT)
                .set(ACCOUNT_CREDIT_LIMIT.TRANSACTIONAGGREGATE, newAggregatedValue.getValue())
                .where(ACCOUNT_CREDIT_LIMIT.ID.eq(id))
                .execute();
    }

    private CreditLimit convertQueryResultToModelObject(AccountCreditLimitRecord persisted) {
        return new CreditLimit(
                persisted.getId(),
                persisted.getAccountId(),
                persisted.getAvailablecreditlimit(),
                persisted.getTransactionaggregate()
        );
    }

    private AccountCreditLimitRecord createRecord(CreditLimit creditLimit) {
        AccountCreditLimitRecord creditLimitRecord = new AccountCreditLimitRecord();
        creditLimitRecord.setId(creditLimit.getId());
        creditLimitRecord.setAccountId(creditLimit.getAccountId());
        creditLimitRecord.setAvailablecreditlimit(creditLimit.getAvailableCreditLimit());
        creditLimitRecord.setTransactionaggregate(creditLimit.getTransactionAggregate());
        return creditLimitRecord;
    }
}
