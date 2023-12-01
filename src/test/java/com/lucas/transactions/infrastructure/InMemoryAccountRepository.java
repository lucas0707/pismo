package com.lucas.transactions.infrastructure;

import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.domain.account.AccountRepository;

import java.util.HashMap;
import java.util.UUID;

public class InMemoryAccountRepository implements AccountRepository {
    private final HashMap<UUID, Account> map;
    public InMemoryAccountRepository() {
        this.map = new HashMap<>();
    }
    @Override
    public Account create(Account account) throws Exception {
        map.put(account.getId(), account);
        return account;
    }

    @Override
    public Account find(UUID accountId) throws Exception {
        return map.get(accountId);
    }

    @Override
    public Boolean any(UUID accountId) {
        return map.containsKey(accountId);
    }
}
