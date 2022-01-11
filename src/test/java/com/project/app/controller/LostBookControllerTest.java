package com.project.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.LoanDTO;
import com.project.app.dto.LostBookDTO;
import com.project.app.entity.*;
import com.project.app.response.PageResponse;
import com.project.app.service.LostBookService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class LostBookControllerTest {

    @Autowired
    MockMvc mockMvc; // sebagai client

    @MockBean
    LostBookService service;

    @Autowired
    ObjectMapper objectMapper;

    private LostBookReport requestLost;
    private LostBookReport outputLost;
    private Book book;
    private User jannes;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        book = new Book("book-1", "buku mtk", 100);

        requestLost = new LostBookReport("lost-book-1", jannes, book, 2, LocalDateTime.now());
        outputLost = new LostBookReport("lost-book-1", jannes, book, 2, LocalDateTime.now());
    }

    @Test
    public void getAllLostBooks_ShouldReturn_StatusOK_and_AllPagedLostBooks() throws Exception {
        Sort sort = Sort.by("dateLost");
        Pageable pageable = PageRequest.of(0,1,sort);
        LostBookDTO dto = new LostBookDTO("book-1", null);

        List<LostBookReport> lostBooks = new ArrayList<>();
        lostBooks.add(outputLost);
        Page<LostBookReport> lostPage = new PageImpl<>(lostBooks,pageable, lostBooks.size());

        // actual
        Mockito.when(service.getAll(Mockito.any(LostBookDTO.class), Mockito.any(Pageable.class))).thenReturn(lostPage);


        // expected
        String message =  String.format("data halaman ke 1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        "/lost-books")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("size", "1")
                .queryParam("page", "0")
                .queryParam("sortBy", "dateLost")
                .queryParam("direction", "ASC")
                .queryParam("bookId", "book-1");


        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        PageResponse<LostBookReport> response = objectMapper.readValue(responseJson, new TypeReference<PageResponse<LostBookReport>>() {});

        assertNotNull(response.getData());
        assertEquals(response.getData().size(), 1);
        assertEquals(response.getTotalContent(), 1);
    }
}