package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.BookSale;
import com.project.app.handler.ResearchPermissionHandler;
import com.project.app.service.BookSaleService;
import com.project.app.service.HandlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
class PermissionRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    HandlerService service;

    @Test
    public void whenGivenValidUrlAndMethodCreateRequest_thenReturn201() throws Exception {
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", "{\"requestId\": \"R01\"}".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("file", "file.png", "image/png", "image".getBytes());

        mockMvc.perform(multipart("/api/v0/requests")
                        .file(request)
                        .file(file1))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenCreateRequestFileIsNull_thenReturnError() throws Exception {
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", "{\"requestId\": \"B01\"}".getBytes());

        mockMvc.perform(multipart("/api/v0/requests")
                        .file(request)
                        .contentType("multipart/form-data"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenRequestAndFileIsNull_thenReturnError() throws Exception {

        mockMvc.perform(multipart("/api/v0/requests")
                        .contentType("multipart/form-data"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenGivenValidUrlAndMethodGetById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v0/requests/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenValidInputGetById_thenReturnExpectedResult() throws Exception {
        String id = "R01";
        ResearchPermissionHandler request = new ResearchPermissionHandler();
        request.setRequestId("R01");
        request.setComment("Comment");

        given(service.getById(id)).willReturn(request);

        mockMvc.perform(get("/api/v0/requests/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.['data'].['requestId']").value(request.getRequestId()))
                .andExpect(jsonPath("$.['data'].['comment']").value(request.getComment()));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetAll_thenReturn200() throws Exception {

        ResearchPermissionHandler request = new ResearchPermissionHandler();
        request.setRequestId("R01");
        request.setComment("Comment");

        Pageable pageable = PageRequest.of(0, 5);

        List<ResearchPermissionHandler> research = new ArrayList<>();
        research.add(request);

        Page<ResearchPermissionHandler> page = new PageImpl<>(research, pageable, 1L);

        assertNotNull(page);

        given(service.fetchRequests(PageRequest.of(0, 5))).willReturn(page);

        mockMvc.perform(get("/api/v0/requests")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenValidUrlAndMethodDeleteBook_thenReturn200() throws Exception {

        mockMvc.perform(delete("/api/v0/requests/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("R01"));
    }

}