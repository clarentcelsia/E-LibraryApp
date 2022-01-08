package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.AdminTransaction;
import com.project.app.response.PageResponse;
import com.project.app.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.app.util.Utility.RESPONSE_CREATE_SUCCESS;
import static com.project.app.util.Utility.RESPONSE_GET_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    TransactionService<AdminTransaction> service;

    @Test
    public void whenGivenValidUrlAndMethodAndInput_thenReturn201() throws Exception {

        AdminTransaction adminTransaction = new AdminTransaction("TRX01");

        given(service.createTransaction(any(AdminTransaction.class))).willReturn(adminTransaction);

        mockMvc.perform(post("/api/v11/transactions/admin")
                        .content(mapper.writeValueAsString(adminTransaction))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['transactionId']").value(adminTransaction.getTransactionId()));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v11/transactions/admin/{id}", "B01")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenValidInputGetById_thenReturnExpectedResult() throws Exception {
        String id = "B01";
        AdminTransaction transaction = new AdminTransaction(id);

        given(service.getTransactionById(id)).willReturn(transaction);

        mockMvc.perform(get("/api/v11/transactions/admin/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$['data'].['transactionId']").value(transaction.getTransactionId()));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetAll_thenReturn200() throws Exception {

        PageResponse<AdminTransaction> page = service.getTransactions(PageRequest.of(0, 5));

        given(service.getTransactions(PageRequest.of(0,5))).willReturn(page);

        mockMvc.perform(get("/api/v11/transactions/admin")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }
}