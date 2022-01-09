package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.ProductionBook;
import com.project.app.handler.ProductionRequestHandler;
import com.project.app.service.ProductionBookService;
import com.project.app.service.ProductionRequestService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.project.app.util.Utility.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductionRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    ProductionRequestService service;

    @Autowired
    ProductionRequestController controller;

    @Test
    public void whenGivenValidUrlAndMethodCreateRequest_thenReturn201() throws Exception {

        ProductionRequestHandler handler = new ProductionRequestHandler();
        handler.setProductionRequestHandlerId("P01");
        handler.setOnHandle(true);

        mockMvc.perform(post("/api/v0/request/production")
                        .content(mapper.writeValueAsString(handler))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenValidInput_thenMapToModel() throws Exception {

        ProductionRequestHandler handler = new ProductionRequestHandler();
        handler.setProductionRequestHandlerId("P01");
        handler.setOnHandle(true);

        mockMvc.perform(post("/api/v0/request/production")
                        .content(mapper.writeValueAsString(handler))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));

        ArgumentCaptor<ProductionRequestHandler> captor = ArgumentCaptor.forClass(ProductionRequestHandler.class);
        verify(service, times(1)).createRequest(captor.capture());
        assertThat(captor.getValue().getProductionRequestHandlerId()).isEqualTo("P01");
        assertThat(captor.getValue().getOnHandle()).isEqualTo(true);
    }

    @Test
    public void whenGivenValidUrlAndMethodGetById_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v0/request/production/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenValidInputGetById_thenReturnExpectedResult() throws Exception {

        ProductionRequestHandler handler = new ProductionRequestHandler();
        handler.setProductionRequestHandlerId("P01");
        handler.setOnHandle(true);

        given(service.getById(handler.getProductionRequestHandlerId())).willReturn(handler);

        mockMvc.perform(get("/api/v0/request/production/{id}", "P01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.['data'].['productionRequestHandlerId']").value(handler.getProductionRequestHandlerId()))
                .andExpect(jsonPath("$.['data'].['onHandle']").value(handler.getOnHandle()));
    }

    @Test
    public void whenGivenValidUrlAndMethodFetchRequest_thenReturn200() throws Exception {

        ProductionRequestHandler handler = new ProductionRequestHandler();
        handler.setProductionRequestHandlerId("P01");
        handler.setOnHandle(true);

        Pageable pageable = PageRequest.of(0, 5);

        List<ProductionRequestHandler> handlers = new ArrayList<>();
        handlers.add(handler);

        Page<ProductionRequestHandler> page = new PageImpl<>(handlers, pageable, 1L);

        assertNotNull(page);

        given(service.fetchRequests(PageRequest.of(0, 5))).willReturn(page);

        mockMvc.perform(get("/api/v0/request/production")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void findRequestByStatus_shouldReturnListOfItem() throws Exception{
        ProductionRequestHandler handler = new ProductionRequestHandler();
        handler.setProductionRequestHandlerId("P01");
        handler.setOnHandle(true);

        List<ProductionRequestHandler> handlers = new ArrayList<>();
        handlers.add(handler);

        when(service.findRequestByStatus(true)).thenReturn(handlers);

        mockMvc.perform(get("/api/v0/request/production/find")
                        .param("status", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    public void whenGivenValidUrlAndMethodUpdateRequest_thenReturn200() throws Exception {

        ProductionRequestHandler handler = new ProductionRequestHandler();
        handler.setProductionRequestHandlerId("P01");
        handler.setOnHandle(true);

        given(service.updateRequest(any(ProductionRequestHandler.class))).willReturn(handler);

        mockMvc.perform(put("/api/v0/request/production")
                        .content(mapper.writeValueAsString(handler))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_UPDATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['productionRequestHandlerId']").value(handler.getProductionRequestHandlerId()))
                .andExpect(jsonPath("$.['data'].['onHandle']").value(true));
    }

    @Test
    public void whenGivenValidUrlAndMethodDeleteRequest_thenReturn200() throws Exception {
        mockMvc.perform(delete("/api/v0/request/production/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("R01"));
    }
}