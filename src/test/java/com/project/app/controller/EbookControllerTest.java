package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.Ebook;
import com.project.app.request.EbookAPI;
import com.project.app.service.EbookService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

        mockMvc.perform(post("/api/v5/ebooks")
                        .content(mapper.writeValueAsString(api))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Success: Data saved successfully!"));
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

        mockMvc.perform(post("/api/v5/ebooks")
                        .content(mapper.writeValueAsString(api))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Success: Data saved successfully!"));

        ArgumentCaptor<EbookAPI> ebookCaptor = ArgumentCaptor.forClass(EbookAPI.class);
        verify(service, times(1)).saveEbookToDB(ebookCaptor.capture());
        assertThat(ebookCaptor.getValue().getEbookCode()).isEqualTo("API01");
        assertThat(ebookCaptor.getValue().getTitle()).isEqualTo("How to use adobe photoshop?");
        assertThat(ebookCaptor.getValue().getPublishedDate()).isEqualTo("1993");
    }

    @Test
    public void whenEbookGetSuccessfully_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v5/ebooks")
                        .param("title", "")
                        .param("page", "0")
                        .param("size", "5")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success: data saved successfully!"));
    }

    @Test
    public void whenEbookGetsByIdSucceed_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v5/ebooks/id/{id}", "R01")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Success: get data successfully!"));
    }


    @Test
    public void whenEbookDeleted_thenReturn200() throws Exception {

        mockMvc.perform(delete("/api/v5/ebooks/{id}", "R01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success: deleted successfully!"))
                .andExpect(jsonPath("$.data").value("R01"));
    }

    @Test
    public void whenDeleteNoIdGiven_thenReturn405() throws Exception {

        mockMvc.perform(delete("/api/v5/ebooks/{id}", ""))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    public void whenGivenValidEbookCodeFromAPISucceed_thenReturn200() throws Exception {
        String ebookCode = "Gv9EGwAACAAJ";

        mockMvc.perform(get("/api/v5/ebooks/{code}", ebookCode)
                        .contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Succeed: requested ebook by code "+ ebookCode  +" successfully!"));
    }

    @Test
    public void whenGetEbooksByAuthorFromAPISucceed_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v5/ebooks/author")
                        .contentType("application/json")
                        .param("q", "flower")
                        .param("author", "keyes"))
                .andExpect(jsonPath("$.message").value("Success: get api successfully!"));
    }

    @Test
    public void whenGetEbooksFromAPISucceed_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v5/ebooks/search")
                        .queryParam("q", "research")
                        .contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Success: get api successfully!"));
    }


}