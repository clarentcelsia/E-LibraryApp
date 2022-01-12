package com.project.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.LoanDTO;
import com.project.app.entity.Book;
import com.project.app.entity.Loan;
import com.project.app.entity.LoanDetail;
import com.project.app.entity.User;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.LoanService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class LoanControllerTest {
    @Autowired
    MockMvc mockMvc; // sebagai client

    @MockBean
    LoanService loanService;

    @Autowired
    ObjectMapper objectMapper;

    private Loan requestLoan;
    private Loan outputLoan;
    private LoanDetail loanDetailOne;
    private Book bookOne;
    private User jannes;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        bookOne = new Book();
        bookOne.setId("book-1");
        bookOne.setTitle("buku mtk");
        bookOne.setStock(100);

        requestLoan = new Loan(null, null, null, jannes, null, null, null, LocalDateTime.now());
        outputLoan = new Loan("loan-1", 1, false, jannes, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.of(LocalDate.of(2022,1,13), LocalTime.now()));
        loanDetailOne = new LoanDetail("loan-detail-1", outputLoan, bookOne, 1);

        outputLoan.getLoanDetail().add(loanDetailOne);
    }

    @Test
    public void getLoanById_shouldReturn_StatusOK_and_RequestedLoan() throws Exception {
        String id = outputLoan.getId();
        String message = "getting loan";
        Mockito.when(loanService.getById(id)).thenReturn(outputLoan);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/loans/loan-1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputLoan.getId())));
    }

    @Test
    public void deleteLoanById_shouldReturn_StatusOK_and_Message() throws Exception {
        String id = outputLoan.getId();
        String message = String.format("loan with id %s is deleted", id);
        Mockito.when(loanService.deleteById(id)).thenReturn(message);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/loans/loan-1")
                .contentType(MediaType.APPLICATION_JSON);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        Response<Loan> response = objectMapper.readValue(responseJson, Response.class);
        assertNull(response.getData());
    }

    @Test
    public void getLoans_ShouldReturn_StatusOK_and_AllPagedLoans() throws Exception {
        // preparation all paged loans
        Sort sort = Sort.by("status");
        Pageable pageable = PageRequest.of(0,1,sort);
        LoanDTO dto = new LoanDTO("false", null);

        List<Loan> loans = new ArrayList<>();
        loans.add(outputLoan);
        Page<Loan> loanPage = new PageImpl<>(loans,pageable, loans.size());

        // actual
        Mockito.when(loanService.getAll(Mockito.any(LoanDTO.class), Mockito.any(Pageable.class))).thenReturn(loanPage);


        // expected
        String message =  String.format("data halaman ke 1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        "/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("size", "1")
                .queryParam("page", "0")
                .queryParam("sortBy", "returnStatus")
                .queryParam("direction", "ASC")
                .queryParam("status", "false");


        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andExpect(jsonPath("$.['data'].['content'][0].['id']", Matchers.is(outputLoan.getId())));
    }

    // transaction testing
    @Test
    public void createTransaction_shouldReturn_StatusCREATED_AND_SavedTransaction_WithOneLoanDetailInLoan_When_PostLoanWithOneLoanDetail() throws Exception {
        String requestJson = objectMapper.writeValueAsString(requestLoan);

        // actual
        Mockito.when(loanService.createTransaction(Mockito.any(Loan.class))).thenReturn(outputLoan);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/loans/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        String loanDetailId = outputLoan.getLoanDetail().get(0).getId();
        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("creating transaction")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputLoan.getId())))
                // assert loanDetail is the same as outputLoan
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.loanDetail.[0].id", Matchers.is(loanDetailId)))
                .andReturn().getResponse().getContentAsString();

    }
}