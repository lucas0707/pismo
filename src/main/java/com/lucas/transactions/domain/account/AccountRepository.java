package com.lucas.transactions.domain.account;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository {
    Account create(Account account) throws Exception;
    Account find(UUID accountId) throws Exception;
    Boolean any(UUID accountId);
}
