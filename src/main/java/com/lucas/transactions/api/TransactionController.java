package com.lucas.transactions.api;

import com.lucas.transactions.api.dto.transaction.CreateTransactionRequest;
import com.lucas.transactions.usecases.transaction.CreateTransactionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TransactionController {
    private final CreateTransactionUseCase createTransactionUseCase;

    @Autowired
    TransactionController(CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }
    @PostMapping("/transactions")
    public ResponseEntity<UUID> createAccount(@RequestBody CreateTransactionRequest request) throws Exception {
        UUID created = createTransactionUseCase.create(request);
        return ResponseEntity.ok(created);
    }
}
