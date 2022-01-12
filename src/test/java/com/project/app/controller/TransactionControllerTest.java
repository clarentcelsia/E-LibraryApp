package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.*;
import com.project.app.response.Response;
import com.project.app.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.project.app.utils.Utility.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TransactionService<Transaction> service;

    @Test
    public void whenGivenValidUrlAndMethod_onCreateTransaction_thenReturn201() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("C01");
        transaction.setGrandtotal(350000);

        mockMvc.perform(post("/api/v10/client/transactions")
                        .content(mapper.writeValueAsString(transaction))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenGivenTransactionIsSucceed_thenMapToModel() throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionId("C01");
        transaction.setGrandtotal(350000);

        mockMvc.perform(post("/api/v10/client/transactions")
                        .content(mapper.writeValueAsString(transaction))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(service, times(1)).createTransaction(captor.capture());
        assertThat(captor.getValue().getTransactionId()).isEqualTo("C01");
        assertThat(captor.getValue().getGrandtotal()).isEqualTo(350000);
    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetTransactionById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v10/client/transactions/{id}", "C01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));

    }

    @Test
    public void whenGetTransactionByIdSucceed_thenReturnTransaction() throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionId("C01");
        transaction.setGrandtotal(350000);

        Response<Transaction> response = new Response<>(RESPONSE_GET_SUCCESS, transaction);

        given(service.getTransactionById(transaction.getTransactionId())).willReturn(response.getData());

        MvcResult result = mockMvc.perform(get("/api/v10/client/transactions/{id}", transaction.getTransactionId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andReturn();

        String expected = mapper.writeValueAsString(response);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);

    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetTransaction_thenReturn200() throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionId("C01");
        transaction.setGrandtotal(350000);

        Pageable pageable = PageRequest.of(0,5);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Page<Transaction> page = new PageImpl<Transaction>(transactions, pageable, 1L);

        assertNotNull(page);

        given(service.getTransactions(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/v10/client/transactions")
                        .param("page", "0")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }


}