package com.lucas.transactions.infrastructure.postgresql;

import com.lucas.transactions.TransactionsApplication;
import com.lucas.transactions.domain.operationType.OperationTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionsApplication.class)
@ActiveProfiles("test")
public class PostgresqlOperationTypesIntegrationTests {
    @Autowired
    OperationTypeRepository operationTypeRepository;

    @Transactional
    @Test
    void shouldReturnIfThereIsAnyOperationTypeTrue() {
        int operationTypeId = 1;
        boolean operationExists = operationTypeRepository.hasOperationType(operationTypeId);
        Assertions.assertTrue(operationExists);
    }

    @Transactional
    @Test
    void shouldReturnIfThereIsAnyOperationTypeFalse() {
        int operationTypeId = 8;
        boolean operationExists = operationTypeRepository.hasOperationType(operationTypeId);
        Assertions.assertFalse(operationExists);
    }
}
