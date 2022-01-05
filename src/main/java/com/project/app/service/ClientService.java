package com.project.app.service;

import com.project.app.entity.Clients;

import java.util.List;

public interface ClientService {

    List<Clients> getClients();

    Clients getClientById(String id);

    Clients saveClient(Clients client);

    Clients updateClient(Clients client);

    void deleteClient(String id);
}
