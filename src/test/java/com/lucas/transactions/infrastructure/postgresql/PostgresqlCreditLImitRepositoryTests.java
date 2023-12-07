package com.lucas.transactions.infrastructure.postgresql;

import com.lucas.transactions.TransactionsApplication;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;
import com.lucas.transactions.domain.creditLimit.CreditLimit;
import com.lucas.transactions.domain.creditLimit.CreditLimitRepository;
import com.lucas.transactions.domain.valueObjects.Amount;
import com.transactions.db.tables.AccountCreditLimit;
import com.transactions.db.tables.records.AccountCreditLimitRecord;
import com.transactions.db.tables.records.AccountsRecord;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.transactions.db.Tables.ACCOUNT_CREDIT_LIMIT;
import static com.transactions.db.tables.Accounts.ACCOUNTS;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionsApplication.class)
@ActiveProfiles("test")
public class PostgresqlCreditLImitRepositoryTests {
    @Autowired
    CreditLimitRepository creditLimitRepository;

    @Autowired
    DSLContext dslContext;

    @Transactional
    @Test
    void shouldSaveAccount() throws Exception {
        UUID accountId = UUID.randomUUID();
        createAccount(accountId);
        String availableCredit = "50000";
        String aggregate = "0";
        CreditLimit creditLimit = new CreditLimit(accountId, availableCredit, aggregate);
        UUID creditLimitId = creditLimitRepository.create(creditLimit).getId();

        boolean exists = dslContext.fetchExists(
                dslContext.selectFrom(ACCOUNT_CREDIT_LIMIT)
                        .where(ACCOUNT_CREDIT_LIMIT.ID.equal(creditLimitId))
        );
        Assertions.assertTrue(exists);
    }

    @Transactional
    @Test
    void shouldFetchCreditLimit() throws Exception {
        UUID accountId = UUID.randomUUID();
        createAccount(accountId);
        UUID creditLimitId = UUID.randomUUID();
        createAccountCreditLimit(creditLimitId, accountId);
        CreditLimit creditLimit = creditLimitRepository.fetchByAccountId(accountId);

        Assertions.assertEquals(creditLimitId, creditLimit.getId());
        Assertions.assertEquals(accountId, creditLimit.getAccountId());
        Assertions.assertEquals("50000", creditLimit.getAvailableCreditLimit());
        Assertions.assertEquals("0", creditLimit.getTransactionAggregate());
    }

    @Transactional
    @Test
    void shouldUpdateCreditLimitAggregatedValue() throws Exception {
        UUID accountId = UUID.randomUUID();
        createAccount(accountId);
        UUID creditLimitId = UUID.randomUUID();
        createAccountCreditLimit(creditLimitId, accountId);

        Amount newAmount = new Amount("15000");

        creditLimitRepository.updateAggregated(newAmount, creditLimitId);

        AccountCreditLimitRecord queryResults = dslContext.selectFrom(ACCOUNT_CREDIT_LIMIT)
                .where(ACCOUNT_CREDIT_LIMIT.ID.equal(creditLimitId))
                .fetchOne();

        CreditLimit creditLimit = convertQueryResultToModelObject(queryResults);

        Assertions.assertEquals(creditLimitId, creditLimit.getId());
        Assertions.assertEquals(accountId, creditLimit.getAccountId());
        Assertions.assertEquals("15000", creditLimit.getTransactionAggregate());
    }

    private CreditLimit convertQueryResultToModelObject(AccountCreditLimitRecord persisted) {
        return new CreditLimit(
                persisted.getId(),
                persisted.getAccountId(),
                persisted.getAvailablecreditlimit(),
                persisted.getTransactionaggregate()
        );
    }

    private void createAccountCreditLimit(UUID creditLimitId, UUID accountId) {
        String availableCredit = "50000";
        String aggregate = "0";
        CreditLimit creditLimit = new CreditLimit(creditLimitId, accountId, availableCredit, aggregate);
        dslContext.insertInto(ACCOUNT_CREDIT_LIMIT)
                .set(createRecord(creditLimit))
                .returning()
                .fetchOne();
    }

    private AccountCreditLimitRecord createRecord(CreditLimit creditLimit) {
        AccountCreditLimitRecord creditLimitRecord = new AccountCreditLimitRecord();
        creditLimitRecord.setId(creditLimit.getId());
        creditLimitRecord.setAccountId(creditLimit.getAccountId());
        creditLimitRecord.setAvailablecreditlimit(creditLimit.getAvailableCreditLimit());
        creditLimitRecord.setTransactionaggregate(creditLimit.getTransactionAggregate());
        return creditLimitRecord;
    }

    private void createAccount(UUID accountId) {
        String documentNumber = "12345678910";
        Account account = new Account(documentNumber, accountId);
        dslContext.insertInto(ACCOUNTS)
                .set(createAccountRecord(account))
                .returning()
                .fetchOne();
    }
    private AccountsRecord createAccountRecord(Account account) {
        AccountsRecord accountsRecord = new AccountsRecord();
        accountsRecord.setAccountId(account.getId());
        accountsRecord.setDocumentNumber(account.getDocumentNumber());
        return accountsRecord;
    }
}
