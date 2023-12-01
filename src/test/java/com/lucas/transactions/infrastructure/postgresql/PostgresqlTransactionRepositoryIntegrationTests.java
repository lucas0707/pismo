package com.lucas.transactions.infrastructure.postgresql;

import com.lucas.transactions.TransactionsApplication;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.transaction.Transaction;
import com.lucas.transactions.domain.transaction.TransactionRepository;
import com.lucas.transactions.domain.valueObjects.Amount;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static com.transactions.db.Tables.TRANSACTIONS;
import static com.transactions.db.tables.Accounts.ACCOUNTS;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionsApplication.class)
@ActiveProfiles("test")
public class PostgresqlTransactionRepositoryIntegrationTests {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DSLContext dslContext;

    @Transactional
    @Test
    void shouldCreateTransaction() throws Exception {
        UUID accountId = UUID.randomUUID();
        createAccount(accountId);
        int operationType = 4;
        UUID transactionId = UUID.randomUUID();
        String amount = "10000";
        Transaction transaction = new Transaction(
                transactionId,
                accountId,
                operationType,
                new Amount(amount),
                LocalDateTime.now()
        );

        Transaction savedTransaction = transactionRepository.create(transaction);

        Assertions.assertEquals(transaction.getId(), savedTransaction.getId());

        boolean exists = dslContext.fetchExists(
                dslContext.selectFrom(TRANSACTIONS)
                        .where(TRANSACTIONS.TRANSACTION_ID.equal(transactionId))
        );
        Assertions.assertTrue(exists);
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
