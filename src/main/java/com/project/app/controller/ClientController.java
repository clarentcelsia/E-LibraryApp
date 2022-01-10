package com.project.app.controller;

import com.project.app.dto.ClientDTO;
import com.project.app.entity.Clients;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.app.util.Utility.*;


@RestController
@RequestMapping("/api/v9/clients")
public class ClientController {

    @Autowired
    ClientService service;

    @PostMapping
    public ResponseEntity<Response<Clients>> saveClient(
            @RequestBody Clients client
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.saveClient(client)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Clients>> getClientById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getClientById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Clients>>> getClients(
            @RequestParam(name = "active", defaultValue = "1") Integer status,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        ClientDTO clientDTO = new ClientDTO(status);
        Page<Clients> clientsPage = service.getClients(clientDTO, PageRequest.of(page, size));
        PageResponse<Clients> response = new PageResponse<>(
                clientsPage.getContent(),
                clientsPage.getTotalElements(),
                clientsPage.getTotalPages(),
                page,
                size
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @PutMapping
    public ResponseEntity<Response<Clients>> updateClient(
            @RequestBody Clients client
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_UPDATE_SUCCESS, service.updateClient(client)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteClients(
            @PathVariable(name = "id") String id
    ) {
        service.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }

}
