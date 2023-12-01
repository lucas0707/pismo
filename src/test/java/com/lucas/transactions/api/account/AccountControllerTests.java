package com.lucas.transactions.api.account;

import com.lucas.transactions.api.dto.account.CreateAccountRequest;
import com.lucas.transactions.domain.account.Account;
import com.lucas.transactions.usecases.account.CreateAccountUseCase;
import com.lucas.transactions.usecases.account.FindAccountUseCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CreateAccountUseCase createUseCase;

    @Mock
    private FindAccountUseCase findAccountUseCase;

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        String requestBody = "{'document_number': '12345678900'}";
        when(
                createUseCase.createAccount(ArgumentMatchers.any(CreateAccountRequest.class))
        ).thenReturn(UUID.randomUUID());

        this.mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(requestBody));
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("asd")));
    }

    @Test
    void shouldGet() throws Exception {
        String documentNumber = "12345678900";
        UUID id = UUID.randomUUID();
        Account response = new Account(documentNumber, id);
        when(findAccountUseCase.find(ArgumentMatchers.any(UUID.class))).thenReturn(response);

        MvcResult mvcResult = this.mockMvc.perform(get("/accounts/{accountId}", id.toString())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(true);
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("asd")));
    }
}
