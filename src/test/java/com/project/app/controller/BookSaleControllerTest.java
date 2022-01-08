package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.BookSale;
import com.project.app.response.PageResponse;
import com.project.app.service.BookSaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.app.util.Utility.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookSaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    BookSaleService service;


    @Test
    public void whenGivenValidUrlAndMethodSavedBook_thenReturn201() throws Exception {
        MockMultipartFile book = new MockMultipartFile("detail", "", "application/json", "{\"bookSaleId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("preview", "file1.txt", "plain/txt", "text1".getBytes());
        MockMultipartFile file3 = new MockMultipartFile("download", "file2.txt", "plain/txt", "text2".getBytes());

        mockMvc.perform(multipart("/api/v11/booksale")
                        .file(book)
                        .file(file1).file(file2).file(file3))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenSaveBookImageIsNull_thenReturnError() throws Exception {
        MockMultipartFile book = new MockMultipartFile("detail", "", "application/json", "{\"bookSaleId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("preview", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v11/booksale")
                        .file(book)
                        .file(file1)
                        .contentType("multipart/form-data"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenPreviewAndDownloadIsNull_thenStillReturnMessageSuccess() throws Exception {
        MockMultipartFile book = new MockMultipartFile("detail", "", "application/json", "{\"bookSaleId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v11/booksale")
                        .file(book)
                        .file(file1)
                        .contentType("multipart/form-data"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenOnlyPreviewIsNull_thenStillReturnMessageSuccess() throws Exception {
        MockMultipartFile book = new MockMultipartFile("detail", "", "application/json", "{\"bookSaleId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("download", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v11/booksale")
                        .file(book)
                        .file(file1)
                        .file(file2)
                        .contentType("multipart/form-data"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenOnlyDownloadIsNull_thenStillReturnMessageSuccess() throws Exception {
        MockMultipartFile book = new MockMultipartFile("detail", "", "application/json", "{\"bookSaleId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("preview", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v11/booksale")
                        .file(book)
                        .file(file1)
                        .file(file2)
                        .contentType("multipart/form-data"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenImageAndBookIsNull_thenReturn400() throws Exception {
        MockMultipartFile preview = new MockMultipartFile("preview", "", "application/json", "{\"bookSaleId\": \"B01\"}".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("download", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v11/booksale")
                        .file(preview)
                        .file(file2)
                        .contentType("multipart/form-data"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenGivenValidUrlAndMethodGetById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v11/booksale/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenValidInputGetById_thenReturnExpectedResult() throws Exception {
        String id = "R01";
        BookSale book = new BookSale("R01", "title");

        given(service.getBookSaleById(id)).willReturn(book);

        mockMvc.perform(get("/api/v11/booksale/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.['data'].['bookSaleId']").value(book.getBookSaleId()))
                .andExpect(jsonPath("$.['data'].['title']").value(book.getTitle()));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetAll_thenReturn200() throws Exception {

        PageResponse<BookSale> page = service.getBookSales(PageRequest.of(0, 5));

        given(service.getBookSales(PageRequest.of(0,5))).willReturn(page);

        mockMvc.perform(get("/api/v11/booksale")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenValidUrlAndMethodDeleteBook_thenReturn200() throws Exception {

        mockMvc.perform(delete("/api/v11/booksale/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("R01"));
    }

}