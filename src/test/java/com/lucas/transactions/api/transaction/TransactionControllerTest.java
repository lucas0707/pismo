package com.lucas.transactions.api.transaction;

import com.lucas.transactions.TransactionsApplication;
import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.api.dto.transaction.CreateTransactionRequest;
import com.lucas.transactions.usecases.account.CreateAccountUseCase;
import com.lucas.transactions.usecases.transaction.CreateTransactionUseCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TransactionsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    CreateTransactionUseCase createTransactionUseCase;

    @Test
    void shouldCreateTransactionAndReturnId() throws Exception {
        UUID accountId = UUID.randomUUID();
        double amount = 100.45;
        int operationTypeId = 4;

        String requestBody = "{\"account_id\": \"" + accountId.toString() +
                "\", \"amount\": \""+ amount +"\", \"operation_type_id\": \"" +
                 operationTypeId +"\" }";
        UUID transactionId = UUID.randomUUID();
        when(
                createTransactionUseCase.create(ArgumentMatchers.any(CreateTransactionRequest.class))
        ).thenReturn(transactionId);

        mvc.perform(
                        post("/v1/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(transactionId.toString())));
    }
}
