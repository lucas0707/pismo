package com.lucas.transactions.infrastructure.postgres;

import com.lucas.transactions.domain.operationType.OperationTypeRepository;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.transactions.db.Tables.OPERATION_TYPES;

@Repository
public class PostgresqlOperationTypeRepository implements OperationTypeRepository {
    private static final Logger logger = LoggerFactory.getLogger(PostgresqlAccountRepository.class);
    private final DSLContext dslContext;

    @Autowired
    public PostgresqlOperationTypeRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    @Override
    public boolean hasOperationType(int id) {
        logger.info("Looking for operation type with id: {}", id);

        return dslContext.fetchExists(
                dslContext.selectFrom(OPERATION_TYPES)
                        .where(OPERATION_TYPES.OPERATION_TYPE_ID.equal(id))
        );
    }
}
