package com.project.app.service;

import com.project.app.dto.ClientDTO;
import com.project.app.entity.Clients;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

    Page<Clients> getClients(ClientDTO clientDTO, Pageable pageable);

    Clients getClientById(String id);

    Clients saveClient(Clients client);

    Clients updateClient(Clients client);

    void deleteClient(String id);
}
