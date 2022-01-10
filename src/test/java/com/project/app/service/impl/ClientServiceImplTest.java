package com.project.app.service.impl;

import com.project.app.dto.ClientDTO;
import com.project.app.entity.Clients;
import com.project.app.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl service;

    @Mock
    ClientRepository repository;

    @Test
    public void whenGetClients_thenReturnClientInPage(){

        Clients client = new Clients(
                "C01",
                "Washington University",
                "Washington Street 21",
                "44209021",
                "washington@unv.com",
                1
        );

        ClientDTO clientDTO = mock(ClientDTO.class);
        Pageable pageable = PageRequest.of(0,5);

        List<Clients> clients = new ArrayList<>();
        clients.add(client);

        Page<Clients> page = new PageImpl<Clients>(clients, pageable, 1L);

        assertThat(page).isNotNull();

        given(repository.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);

        Page<Clients> clientsPage = service.getClients(clientDTO, pageable);

        assertEquals(page.getContent(), clientsPage.getContent());

    }

    @Test
    public void whenGetClientByIdSucceed_theReturnClient(){
        Clients clients = new Clients(
                "C01",
                "Washington University",
                "Washington Street 21",
                "44209021",
                "washington@unv.com",
                1
        );

        given(repository.findById(any(String.class))).willReturn(Optional.of(clients));

        Clients client = service.getClientById(clients.getClientId());

        assertNotNull(client);

        assertEquals(clients.getClientId(), client.getClientId());

        assertEquals(clients, client);
    }

    @Test
    public void whenSaveClientSucceed_thenReturnClient(){
        Clients clients = new Clients(
                "C01",
                "Washington University",
                "Washington Street 21",
                "44209021",
                "washington@unv.com",
                1
        );

        given(repository.save(any(Clients.class))).willReturn(clients);

        Clients saveClient = service.saveClient(clients);

        assertThat(saveClient).isNotNull();

    }

    @Test
    public void whenUpdateClientSucceed_thenReturnClient(){
        Clients clients = new Clients(
                "C01",
                "Washington University",
                "Washington Street 21",
                "44209021",
                "washington@unv.com",
                1
        );

        given(repository.save(clients)).willReturn(clients);

        given(repository.findById(any(String.class))).willReturn(Optional.of(clients));

        Clients updateClient = service.updateClient(clients);

        assertThat(updateClient).isNotNull();

        //check repo do save(client) once after service update client being called
        verify(repository, times(1)).save(clients);
    }

    @Test
    public void whenDeleteClientSucceed_thenClientDeleteChange(){
        Clients clients = new Clients(
                "C01",
                "Washington University",
                "Washington Street 21",
                "44209021",
                "washington@unv.com",
                1
        );
        clients.setDeleted(false);

        given(repository.save(any(Clients.class))).willReturn(clients);

        given(repository.findById(any(String.class))).willReturn(Optional.of(clients));

        Clients clients1 = service.getClientById(clients.getClientId());

        service.deleteClient(clients1.getClientId());

        assertEquals(true, clients1.getDeleted());

    }

}