package com.project.app.service.impl;

import com.project.app.entity.Clients;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.ClientRepository;
import com.project.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository repository;

    @Override
    public List<Clients> getClients() {
        return repository.findAll();
    }

    @Override
    public Clients getClientById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: data with id "+ id + " not found"));
    }

    @Override
    public Clients saveClient(Clients client) {
        return repository.save(client);
    }

    @Override
    public Clients updateClient(Clients client) {
        Clients clientById = getClientById(client.getClientId());
        clientById.setName(client.getName());
        clientById.setAddress(client.getAddress());
        clientById.setEmail(client.getEmail());
        clientById.setPhoneNumber(client.getPhoneNumber());
        clientById.setStatus(client.getStatus());
        return saveClient(clientById);
    }

    @Override
    public void deleteClient(String id) {
        Clients client = getClientById(id);
        client.setDeleted(true);
        repository.save(client);
    }
}
