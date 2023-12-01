package com.lucas.transactions.api;

import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.api.dto.account.CreateAccountResponse;
import com.lucas.transactions.api.dto.account.FindAccountResponse;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.usecases.account.CreateAccountService;
import com.lucas.transactions.usecases.account.FindAccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AccountController {
    private final CreateAccountService createAccountService;
    private final FindAccountUseCase findAccountUseCase;

    @Autowired
    AccountController(
            CreateAccountService createAccountService,
            FindAccountUseCase findAccountUseCase
    ) {
        this.createAccountService = createAccountService;
        this.findAccountUseCase = findAccountUseCase;
    }

    @PostMapping("/accounts")
    public ResponseEntity<CreateAccountResponse> createAccount(
            @RequestBody CreateAccountRequest request
    ) throws Exception {
        UUID createdId = createAccountService.createAccount(request);
        return ResponseEntity.ok(new CreateAccountResponse(createdId));
    }

    @GetMapping("/accounts/{accountId}")
    ResponseEntity<FindAccountResponse> getAccount(@PathVariable("accountId") UUID accountId) throws Exception {
        Account account = findAccountUseCase.find(accountId);
        return ResponseEntity.ok(new FindAccountResponse(account));
    }
}
