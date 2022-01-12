package com.project.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.LoanDTO;
import com.project.app.dto.ReturnDTO;
import com.project.app.entity.*;
import com.project.app.response.PageResponse;
import com.project.app.service.ReturnBookService;
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

@SpringBootTest
@AutoConfigureMockMvc
class ReturnTransactionControllerTest {
    @Autowired
    MockMvc mockMvc; // sebagai client

    @MockBean
    ReturnBookService service;

    @Autowired
    ObjectMapper objectMapper;

    private ReturnBook requestReturn;
    private ReturnBook outputReturn;
    private Loan loanOne;
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
        loanOne = new Loan("loan-1", 1, false, jannes, null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.of(LocalDate.of(2022,1,13), LocalTime.now()));

        requestReturn = new ReturnBook();

        ReturnBookDetail returnBookDetailOne = new ReturnBookDetail();
        returnBookDetailOne.setId("return-detail-1");
        returnBookDetailOne.setBook(bookOne);
        returnBookDetailOne.setQty(1);
        returnBookDetailOne.setReturnInfo("ok");
        returnBookDetailOne.setLostFee(0);
        outputReturn = new ReturnBook("return-book-1", loanOne, new ArrayList<>(), 1, LocalDateTime.now(), 0, 1000);
        outputReturn.getReturnBookDetails().add(returnBookDetailOne);
    }

    @Test
    public void getLoans_ShouldReturn_StatusOK_and_AllPagedReturnBooks() throws Exception {
        Sort sort = Sort.by("status");
        Pageable pageable = PageRequest.of(0,1,sort);

        ReturnDTO dto = new ReturnDTO(1, null, null);

        List<ReturnBook> returnBooks = new ArrayList<>();
        returnBooks.add(outputReturn);
        Page<ReturnBook> returnPage = new PageImpl<>(returnBooks,pageable, returnBooks.size());

        // actual
        Mockito.when(service.getReturnBooks(Mockito.any(ReturnDTO.class), Mockito.any(Pageable.class))).thenReturn(returnPage);

        // expected
        String message =  String.format("data halaman ke 1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        "/returns")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("size", "1")
                .queryParam("page", "0")
                .queryParam("sortBy", "totalQty")
                .queryParam("direction", "ASC")
                .queryParam("totalQty", "1");


        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        PageResponse<ReturnBook> response = objectMapper.readValue(responseJson, new TypeReference<PageResponse<ReturnBook>>() {});

        assertNotNull(response.getData());
        assertEquals(response.getData().size(), 1);
        assertEquals(response.getTotalContent(), 1);
    }

    // transaction testing
    @Test
    public void createTransaction_shouldReturn_StatusCREATED_AND_SavedTransaction() throws Exception {
        String requestJson = objectMapper.writeValueAsString(requestReturn);

        // actual
        Mockito.when(service.createTransaction(Mockito.any(ReturnBook.class))).thenReturn(outputReturn);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/returns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        String returnBookDetailId = outputReturn.getReturnBookDetails().get(0).getId();
        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("creating transaction")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputReturn.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.returnBookDetails.[0].id", Matchers.is(returnBookDetailId)))
                .andReturn().getResponse().getContentAsString();

    }
}