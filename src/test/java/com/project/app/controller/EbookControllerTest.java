package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.request.EbookAPI;
import com.project.app.service.EbookService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.project.app.utils.Utility.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EbookControllerTest {

    @MockBean
    EbookService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void whenGivenValidUrlAndMethodAndContentTypeEbookSaved_thenReturn201() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("hello");

        EbookAPI api = new EbookAPI(
                "API01", "How to use adobe photoshop?",
                strings, "1993", "erlangga",
                "description", "imageLink",
                "webReaderLink");

        mockMvc.perform(post("/ebooks")
                        .content(mapper.writeValueAsString(api))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenGivenValidInput_thenMapToBusinessModel() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("hello");

        EbookAPI api = new EbookAPI(
                "API01", "How to use adobe photoshop?",
                strings, "1993", "erlangga",
                "description", "imageLink",
                "webReaderLink");

        mockMvc.perform(post("/ebooks")
                        .content(mapper.writeValueAsString(api))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));

        ArgumentCaptor<EbookAPI> ebookCaptor = ArgumentCaptor.forClass(EbookAPI.class);
        verify(service, times(1)).saveEbookToDB(ebookCaptor.capture());
        assertThat(ebookCaptor.getValue().getEbookCode()).isEqualTo("API01");
        assertThat(ebookCaptor.getValue().getTitle()).isEqualTo("How to use adobe photoshop?");
        assertThat(ebookCaptor.getValue().getPublishedDate()).isEqualTo("1993");
    }

    @Test
    public void whenEbookGetsByIdSucceed_thenReturn200() throws Exception {

        mockMvc.perform(get("/ebooks/id/{id}", "R01")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(RESPONSE_GET_SUCCESS));
    }


    @Test
    public void whenEbookDeleted_thenReturn200() throws Exception {

        mockMvc.perform(delete("/ebooks/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("R01"));
    }

    @Test
    public void whenDeleteNoIdGiven_thenReturn405() throws Exception {

        mockMvc.perform(delete("/ebooks/{id}", ""))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    public void whenGivenValidEbookCodeFromAPISucceed_thenReturn200() throws Exception {
        String ebookCode = "Gv9EGwAACAAJ";

        mockMvc.perform(get("/api/v5/ebooks/{code}", ebookCode)
                        .contentType("application/json"))
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenValidUrlAndMethodGetEbooksByAuthorAndTitleFromAPI_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v5/ebooks/search")
                        .contentType("application/json")
                        .param("q", "flower")
                        .param("author", "keyes"))
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGetEbooksByTitleOnlyFromAPISucceed_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v5/ebooks/search")
                        .queryParam("q", "research")
                        .contentType("application/json"))
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }


}