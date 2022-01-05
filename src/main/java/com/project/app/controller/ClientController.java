package com.project.app.controller;

import com.project.app.entity.Clients;
import com.project.app.response.Response;
import com.project.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
                .body(new Response<>("Succeed: client saved successfully!", service.saveClient(client)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Clients>> getClientById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: client saved successfully!", service.getClientById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<List<Clients>>> getClients() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: get client successfully!", service.getClients()));
    }

    @PutMapping
    public ResponseEntity<Response<Clients>> updateClient(
            @RequestBody Clients client
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: update client successfully!", service.updateClient(client)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> getClients(
            @PathVariable(name = "id") String id
    ) {
        service.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: delete client successfully!", id));
    }

}
