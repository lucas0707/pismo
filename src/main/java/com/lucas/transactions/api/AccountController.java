package com.lucas.transactions.api;

import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.api.dto.account.CreateAccountResponse;
import com.lucas.transactions.api.dto.account.FindAccountResponse;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.usecases.account.CreateAccountUseCase;
import com.lucas.transactions.usecases.account.FindAccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final FindAccountUseCase findAccountUseCase;

    @Autowired
    AccountController(
            CreateAccountUseCase createAccountUseCase,
            FindAccountUseCase findAccountUseCase
    ) {
        this.createAccountUseCase = createAccountUseCase;
        this.findAccountUseCase = findAccountUseCase;
    }

    @PostMapping("/v1/accounts")
    public ResponseEntity<CreateAccountResponse> createAccount(
            @RequestBody CreateAccountRequest request
    ) throws Exception {
        UUID createdId = createAccountUseCase.createAccount(request);
        return ResponseEntity.ok(new CreateAccountResponse(createdId));
    }

    @GetMapping("/v1/accounts/{accountId}")
    ResponseEntity<FindAccountResponse> getAccount(@PathVariable("accountId") UUID accountId) throws Exception {
        Account account = findAccountUseCase.find(accountId);
        return ResponseEntity.ok(new FindAccountResponse(account));
    }
}
