package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.Research;
import com.project.app.entity.User;
import com.project.app.response.PageResponse;
import com.project.app.service.ResearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.app.util.Utility.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ResearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    ResearchService service;

    @Test
    public void whenGivenValidUrlAndMethodSaveResearch_thenReturn201() throws Exception {
        Research research = new Research(
                new User("U01", "iliana"),
                "researchUrl");

        given(service.saveResearch(any(Research.class))).willReturn(research);

        mockMvc.perform(post("/api/v3/research")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(research)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['researchId']").value(research.getResearchId()));
    }


    @Test
    public void whenGivenValidUrlAndMethodGetResearch_thenReturn200() throws Exception {

        PageResponse<Research> response = service.getResearch(PageRequest.of(0, 5));

        given(service.getResearch(PageRequest.of(0, 5))).willReturn(response);

        mockMvc.perform(get("/api/v3/research")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenUrlAndMethodGetByIdResearch_thenReturn200() throws Exception {
        Research research = new Research(
                new User("U01", "iliana"),
                "researchUrl");

        research.setResearchId("R01");

        given(service.getById(any(String.class))).willReturn(research);

        mockMvc.perform(get("/api/v3/research/{id}", research.getResearchId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.['data'].['researchId']").value(research.getResearchId()));
    }

    @Test
    public void whenGivenUrlAndMethodDeleteResearch_thenReturn200() throws Exception {
        Research research = new Research(
                new User("U01", "iliana"),
                "researchUrl");

        research.setResearchId("R01");

        given(service.saveResearch(any(Research.class))).willReturn(research);

        mockMvc.perform(delete("/api/v3/research/{id}", research.getResearchId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.['data']").value(research.getResearchId()));

        assertThat(service.getById(research.getResearchId())).isNull();
    }

}