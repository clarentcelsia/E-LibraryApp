package com.project.app.service.impl;

import com.project.app.dto.ClientDTO;
import com.project.app.entity.Clients;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.ClientRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.ClientService;
import com.project.app.specification.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.project.app.util.Utility.RESPONSE_NOT_FOUND;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository repository;

    @Override
    public Page<Clients> getClients(ClientDTO clientDTO, Pageable pageable) {
        Specification<Clients> specification = ClientSpecification.getSpecification(clientDTO);
        return  repository.findAll(specification, pageable);
    }

    @Override
    public Clients getClientById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(RESPONSE_NOT_FOUND, id)));
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
        if (client.getStatus() != null)
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
