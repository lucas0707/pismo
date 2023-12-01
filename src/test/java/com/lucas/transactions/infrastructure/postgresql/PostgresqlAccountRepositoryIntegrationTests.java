package com.lucas.transactions.infrastructure.postgresql;

import com.lucas.transactions.TransactionsApplication;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;
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

import static com.transactions.db.tables.Accounts.ACCOUNTS;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionsApplication.class)
@ActiveProfiles("test")
public class PostgresqlAccountRepositoryIntegrationTests {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DSLContext dslContext;

    @Transactional
    @Test
    void shouldSaveAccount() throws Exception {
        String documentNumber = "12345678901";
        Account account = new Account(documentNumber);
        UUID accountId = accountRepository.create(account).getId();

        boolean exists = dslContext.fetchExists(
                dslContext.selectFrom(ACCOUNTS)
                .where(ACCOUNTS.ACCOUNT_ID.equal(accountId))
        );
        Assertions.assertTrue(exists);
    }

    @Transactional
    @Test
    void shouldFetchAccount() throws Exception {
        UUID accountId = UUID.randomUUID();
        createAccount(accountId);

    @Autowired
    DSLContext dslContext;
        Account account = accountRepository.find(accountId);

        Assertions.assertEquals(accountId, account.getId());
    }

    @Transactional
    @Test
    void shouldReturnIfThereIsAnyAccountTrue() {
        UUID accountId = UUID.randomUUID();
        createAccount(accountId);

        boolean accountExists = accountRepository.any(accountId);

        Assertions.assertTrue(accountExists);
    }

    @Transactional
    @Test
    void shouldReturnIfThereIsAnyAccountFalse() {
        UUID accountId = UUID.randomUUID();

        boolean accountExists = accountRepository.any(accountId);

        Assertions.assertFalse(accountExists);
    }

    private void createAccount(UUID accountId) {
        String documentNumber = "12345678910";
        Account account = new Account(documentNumber, accountId);
        dslContext.insertInto(ACCOUNTS)
                .set(createRecord(account))
                .returning()
                .fetchOne();
    }
    private AccountsRecord createRecord(Account account) {
        AccountsRecord accountsRecord = new AccountsRecord();
        accountsRecord.setAccountId(account.getId());
        accountsRecord.setDocumentNumber(account.getDocumentNumber());
        return accountsRecord;
    }
}
