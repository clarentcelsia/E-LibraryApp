package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.Features;
import com.project.app.response.Response;
import com.project.app.service.FeatureService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import static com.project.app.util.Utility.*;
import static com.project.app.util.Utility.RESPONSE_DELETE_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeatureController.class)
class FeatureControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FeatureService service;


    @Test
    public void whenGivenValidUrlAndMethod_onSaveFeature_thenReturn201() throws Exception {

        Features features = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        mockMvc.perform(post("/api/v8/features")
                        .content(mapper.writeValueAsString(features))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenGivenFeatureIsSucceed_thenMapToModel() throws Exception {

        Features features = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        mockMvc.perform(post("/api/v8/features")
                        .content(mapper.writeValueAsString(features))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));

        ArgumentCaptor<Features> captor = ArgumentCaptor.forClass(Features.class);
        verify(service, times(1)).saveFeature(captor.capture());
        assertThat(captor.getValue().getFeatureId()).isEqualTo("C01");
        assertThat(captor.getValue().getName()).isEqualTo("Loan system");
        assertThat(captor.getValue().getDescription()).isEqualTo("Handle book lending");
    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetFeatureById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v8/features/{id}", "C01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));

    }

    @Test
    public void whenGetFeatureByIdSucceed_thenReturnFeature() throws Exception {

        Features features = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        Response<Features> response = new Response<>(RESPONSE_GET_SUCCESS, features);

        given(service.getFeatureById(features.getFeatureId())).willReturn(response.getData());

        MvcResult result = mockMvc.perform(get("/api/v8/features/{id}", features.getFeatureId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andReturn();

        String expected = mapper.writeValueAsString(response);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);

    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetFeatures_thenReturn200() throws Exception {

        Features feature = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        Pageable pageable = PageRequest.of(0,5);

        List<Features> features = new ArrayList<>();
        features.add(feature);

        Page<Features> page = new PageImpl<Features>(features, pageable, 1L);

        assertNotNull(page);

        given(service.getFeatures(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/v8/features")
                        .param("page", "0")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenValidUrlAndMethod_onUpdateFeature_thenReturn200() throws Exception {
        Features updatedFeatures = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        given(service.updateFeature(any(Features.class))).willReturn(updatedFeatures);

        mockMvc.perform(put("/api/v8/features")
                        .content(mapper.writeValueAsString(updatedFeatures))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_UPDATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['name']").value(updatedFeatures.getName()));
    }

    @Test
    public void whenGivenValidUrlAndMethod_onDeleteFeature_thenReturn200() throws Exception {

        mockMvc.perform(delete("/api/v8/features/{id}", "C01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("C01"));
    }
}