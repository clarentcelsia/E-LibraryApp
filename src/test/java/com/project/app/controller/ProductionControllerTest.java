package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.ProductionBook;
import com.project.app.service.ProductionBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static com.project.app.utils.Utility.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    ProductionBookService service;

    @Autowired
    ProductionController controller;

    @Test
    public void whenGivenValidUrlAndMethodSavedProductionBook_thenReturn201() throws Exception {
        MockMultipartFile book = new MockMultipartFile("book", "", "application/json", "{\"productionBookId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("preview", "file1.txt", "plain/txt", "text1".getBytes());
        MockMultipartFile file3 = new MockMultipartFile("download", "file2.txt", "plain/txt", "text2".getBytes());

        mockMvc.perform(multipart("/api/v3/productions")
                        .file(book)
                        .file(file1).file(file2).file(file3))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenSaveBookImageIsNull_thenReturnError() throws Exception {
        MockMultipartFile book = new MockMultipartFile("book", "", "application/json", "{\"productionBookId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("preview", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v3/productions")
                        .file(book)
                        .file(file1)
                        .contentType("multipart/form-data"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenPreviewAndDownloadIsNull_thenStillReturnMessageSuccess() throws Exception {
        MockMultipartFile book = new MockMultipartFile("book", "", "application/json", "{\"productionBookId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v3/productions")
                        .file(book)
                        .file(file1)
                        .contentType("multipart/form-data"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenOnlyPreviewIsNull_thenStillReturnMessageSuccess() throws Exception {
        MockMultipartFile book = new MockMultipartFile("book", "", "application/json", "{\"productionBookId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("download", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v3/productions")
                        .file(book)
                        .file(file1)
                        .file(file2)
                        .contentType("multipart/form-data"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenOnlyDownloadIsNull_thenStillReturnMessageSuccess() throws Exception {
        MockMultipartFile book = new MockMultipartFile("book", "", "application/json", "{\"productionBookId\": \"B01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("image", "file.png", "image/png", "image".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("preview", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v3/productions")
                        .file(book)
                        .file(file1)
                        .file(file2)
                        .contentType("multipart/form-data"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenImageAndBookIsNull_thenReturn400() throws Exception {
        MockMultipartFile preview = new MockMultipartFile("preview", "", "application/json", "{\"productionBookId\": \"B01\"}".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("download", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v3/productions")
                        .file(preview)
                        .file(file2)
                        .contentType("multipart/form-data"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenGivenValidUrlAndMethodGetById_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v3/productions/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenValidInputGetById_thenReturnExpectedResult() throws Exception {
        String id = "R01";
        ProductionBook book = new ProductionBook("R01", "title");

        given(service.getById(id)).willReturn(book);

        mockMvc.perform(get("/api/v3/productions/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.['data'].['productionBookId']").value(book.getProductionBookId()))
                .andExpect(jsonPath("$.['data'].['title']").value(book.getTitle()));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetAll_thenReturn200() throws Exception {

        ProductionBook productionBook = new ProductionBook("B01", "title");

        Pageable pageable = PageRequest.of(0,5);

        List<ProductionBook> books = new ArrayList<>();
        books.add(productionBook);

        Page<ProductionBook> page = new PageImpl<>(books, pageable, 1L);

        assertNotNull(page);

        given(service.getAll(PageRequest.of(0,5))).willReturn(page);

        mockMvc.perform(get("/api/v3/productions")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenValidUrlAndMethodDeleteBook_thenReturn200() throws Exception {
        mockMvc.perform(delete("/api/v3/productions/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("R01"));
    }


}