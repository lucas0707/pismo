package com.lucas.transactions.api.account;

import com.google.gson.Gson;
import com.lucas.transactions.TransactionsApplication;
import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.usecases.account.CreateAccountService;
import com.lucas.transactions.usecases.account.FindAccountUseCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TransactionsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    CreateAccountService createAccountService;

    @MockBean
    FindAccountUseCase findAccountUseCase;

    @Test
    void shouldCreateAndReturnId() throws Exception {
        String requestBody = "{\"document_number\": \"12345678901\"}";
        UUID accountId = UUID.randomUUID();
        when(
                createAccountService.createAccount(ArgumentMatchers.any(CreateAccountRequest.class))
        ).thenReturn(accountId);

        mvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(accountId.toString())));
    }

    @Test
    void shouldGet() throws Exception {
        String documentNumber = "12345678900";
        UUID id = UUID.randomUUID();
        Account response = new Account(documentNumber, id);
        when(findAccountUseCase.find(ArgumentMatchers.any(UUID.class))).thenReturn(response);

        mvc.perform(get("/accounts/{accountId}", id.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(documentNumber)));
    }
}
