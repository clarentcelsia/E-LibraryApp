package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.ClientDTO;
import com.project.app.entity.Clients;
import com.project.app.response.Response;
import com.project.app.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientService service;

    @Test
    public void whenGivenValidUrlAndMethod_onSaveClient_thenReturn201() throws Exception {

        Clients clients = new Clients(
                "C01",
                "Cambridge University",
                "Cambridge Street 21",
                "44-2938349",
                "cambridge@unv.com",
                1
        );

        mockMvc.perform(post("/api/v9/clients")
                        .content(mapper.writeValueAsString(clients))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenGivenClientIsSucceed_thenMapToModel() throws Exception {

        Clients clients = new Clients(
                "C01",
                "Cambridge University",
                "Cambridge Street 21",
                "44-2938349",
                "cambridge@unv.com",
                1
        );

        mockMvc.perform(post("/api/v9/clients")
                        .content(mapper.writeValueAsString(clients))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));

        ArgumentCaptor<Clients> captor = ArgumentCaptor.forClass(Clients.class);
        verify(service, times(1)).saveClient(captor.capture());
        assertThat(captor.getValue().getClientId()).isEqualTo("C01");
        assertThat(captor.getValue().getName()).isEqualTo("Cambridge University");
        assertThat(captor.getValue().getEmail()).isEqualTo("cambridge@unv.com");
        assertThat(captor.getValue().getStatus()).isEqualTo(1);

    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetClientById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v9/clients/{id}", "C01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));

    }

    @Test
    public void whenGetClientByIdSucceed_thenReturnClient() throws Exception {

        Clients clients = new Clients(
                "C01",
                "Cambridge University",
                "Cambridge Street 21",
                "44-2938349",
                "cambridge@unv.com",
                1
        );

        Response<Clients> response = new Response<>(RESPONSE_GET_SUCCESS, clients);

        given(service.getClientById(clients.getClientId())).willReturn(response.getData());

        MvcResult result = mockMvc.perform(get("/api/v9/clients/{id}", clients.getClientId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andReturn();

        String expected = mapper.writeValueAsString(response);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);

    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetClients_thenReturn200() throws Exception {
        Clients client = new Clients(
                "C01",
                "Washington University",
                "Washington Street 21",
                "44209021",
                "washington@unv.com",
                1
        );

        Pageable pageable = PageRequest.of(0,5);

        List<Clients> clients = new ArrayList<>();
        clients.add(client);

        Page<Clients> page = new PageImpl<Clients>(clients, pageable, 1L);

        assertNotNull(page);

        given(service.getClients(any(ClientDTO.class), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/v9/clients")
                        .param("active", "1")
                        .param("page", "0")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenValidUrlAndMethod_onUpdateClient_thenReturn200() throws Exception {
        Clients updatedClient = new Clients(
                "C01",
                "Oxford University",
                "Oxford Street 21",
                "44-2938349",
                "oxford@unv.com",
                1
        );

        given(service.updateClient(any(Clients.class))).willReturn(updatedClient);

        mockMvc.perform(put("/api/v9/clients")
                        .content(mapper.writeValueAsString(updatedClient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_UPDATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['name']").value(updatedClient.getName()));
    }

    @Test
    public void whenGivenValidUrlAndMethod_onDeleteClient_thenReturn200() throws Exception {

        mockMvc.perform(delete("/api/v9/clients/{id}", "C01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("C01"));
    }

}