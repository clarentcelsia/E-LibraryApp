package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.AdminTransaction;
import com.project.app.response.PageResponse;
import com.project.app.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.project.app.utils.Utility.RESPONSE_CREATE_SUCCESS;
import static com.project.app.utils.Utility.RESPONSE_GET_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
//    @WithMockUser(authorities = {"ROLE_ADMIN"},username = "iliana123", password = "iliana12")
    public void whenGivenValidUrlAndMethodAndInput_thenReturn201() throws Exception {
        AdminTransaction adminTransaction = new AdminTransaction("TRX01");

        given(service.createTransaction(any(AdminTransaction.class))).willReturn(adminTransaction);

        mockMvc.perform(post("/admin-transactions")
//                        .with(SecurityMockMvcRequestPostProcessors.user("iliana123").roles("admin"))
                        .content(mapper.writeValueAsString(adminTransaction))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['transactionId']").value(adminTransaction.getTransactionId()));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetById_thenReturn200() throws Exception {

        mockMvc.perform(get("/admin-transactions/{id}", "B01")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenValidInputGetById_thenReturnExpectedResult() throws Exception {
        String id = "B01";
        AdminTransaction transaction = new AdminTransaction(id);

        given(service.getTransactionById(id)).willReturn(transaction);

        mockMvc.perform(get("/admin-transactions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$['data'].['transactionId']").value(transaction.getTransactionId()));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetAll_thenReturn200() throws Exception {

        AdminTransaction adminTransaction = new AdminTransaction("A01");

        Pageable pageable = PageRequest.of(0,5);

        List<AdminTransaction> admins = new ArrayList<>();
        admins.add(adminTransaction);

        Page<AdminTransaction> page = new PageImpl<>(admins, pageable, 1L);

        assertNotNull(page);

        given(service.getTransactions(PageRequest.of(0,5))).willReturn(page);

        mockMvc.perform(get("/admin-transactions")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }
}