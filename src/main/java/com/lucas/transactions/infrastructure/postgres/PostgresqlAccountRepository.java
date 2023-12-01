package com.lucas.transactions.infrastructure.postgres;

import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;
import com.transactions.db.tables.records.AccountsRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.transactions.db.tables.Accounts.ACCOUNTS;

@Repository
public class PostgresqlAccountRepository implements AccountRepository {
    private static final Logger logger = LoggerFactory.getLogger(PostgresqlAccountRepository.class);
    private final DSLContext dslContext;

    @Autowired
    public PostgresqlAccountRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    @Override
    public Account create(Account account) throws Exception {
        logger.info("Adding new account with document number: {}", account.getDocumentNumber());
        AccountsRecord persisted = dslContext.insertInto(ACCOUNTS)
                .set(createRecord(account))
                .returning()
                .fetchOne();

        if (persisted == null) {
            //@TODO Create custom exception for this.
            throw new Exception("Failed to create account.");
        }

        return convertQueryResultToModelObject(persisted);
    }

    @Transactional(readOnly = true)
    @Override
    public Account find(UUID accountId) throws Exception {
        logger.info("Looking for account with id: {}", accountId);

        AccountsRecord queryResults = dslContext.selectFrom(ACCOUNTS)
                .where(ACCOUNTS.ACCOUNT_ID.equal(accountId))
                .fetchOne();

        if (queryResults == null) {
            //@TODO Create custom exception for this.
            throw new Exception("No account found with id: " + accountId);
        }

        return convertQueryResultToModelObject(queryResults);
    }

    @Override
    public Boolean any(UUID accountId) {
        logger.info("Looking for account with id: {}", accountId);

        return dslContext.fetchExists(
                dslContext.selectFrom(ACCOUNTS)
                .where(ACCOUNTS.ACCOUNT_ID.equal(accountId))
        );
    }

    private Account convertQueryResultToModelObject(AccountsRecord persisted) {
        return new Account(persisted.getDocumentNumber(), persisted.getAccountId());
    }

    private AccountsRecord createRecord(Account account) {
        AccountsRecord accountsRecord = new AccountsRecord();
        accountsRecord.setAccountId(account.getId());
        accountsRecord.setDocumentNumber(account.getDocumentNumber());
        return accountsRecord;
    }
}
