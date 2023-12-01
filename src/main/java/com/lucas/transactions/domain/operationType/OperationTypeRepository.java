package com.lucas.transactions.domain.operationType;

import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository {
    boolean hasOperationType(int id);
}
