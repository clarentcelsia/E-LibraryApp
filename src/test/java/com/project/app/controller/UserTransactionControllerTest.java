package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.AdminTransaction;
import com.project.app.entity.User;
import com.project.app.entity.UserTransaction;
import com.project.app.response.PageResponse;
import com.project.app.service.BookSaleService;
import com.project.app.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static com.project.app.util.Utility.RESPONSE_CREATE_SUCCESS;
import static com.project.app.util.Utility.RESPONSE_GET_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    TransactionService<UserTransaction> service;


    @Test
    public void whenGivenValidUrlAndMethodAndInput_thenReturn201() throws Exception {
        Date date = mock(Date.class);

        UserTransaction userTransaction = new UserTransaction("TRX01", date);

        given(service.createTransaction(any(UserTransaction.class))).willReturn(userTransaction);

        mockMvc.perform(post("/api/v11/transactions/user")
                        .content(mapper.writeValueAsString(userTransaction))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['userTransactionId']").value(userTransaction.getUserTransactionId()))
                .andExpect(jsonPath("$.['data'].['transactionDate']").isNotEmpty());

    }

    @Test
    public void whenGivenValidUrlAndMethodGetById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v11/transactions/user/{id}", "B01")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenValidInputGetById_thenReturnExpectedResult() throws Exception {
        Date date = mock(Date.class);

        UserTransaction userTransaction = new UserTransaction("TRX01", date);

        given(service.getTransactionById(userTransaction.getUserTransactionId())).willReturn(userTransaction);

        mockMvc.perform(get("/api/v11/transactions/user/{id}", "TRX01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$['data'].['userTransactionId']").value(userTransaction.getUserTransactionId()))
                .andExpect(jsonPath("$.['data'].['transactionDate']").isNotEmpty());
    }

    @Test
    public void whenGivenValidUrlAndMethodGetAll_thenReturn200() throws Exception {

        PageResponse<UserTransaction> page = service.getTransactions(PageRequest.of(0, 5));

        given(service.getTransactions(PageRequest.of(0, 5))).willReturn(page);

        mockMvc.perform(get("/api/v11/transactions/user")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }
}