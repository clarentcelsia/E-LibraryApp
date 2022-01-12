package com.project.app.controller;

import com.project.app.handler.ResearchPermissionHandler;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.HandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.project.app.utils.Utility.*;

@RestController
@RequestMapping("/requests")
public class PermissionRequestController {

    @Autowired
    HandlerService service;

    @PostMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<Response<ResearchPermissionHandler>> createRequest(
            @RequestPart(name = "request") ResearchPermissionHandler request,
            @RequestPart(name = "file") MultipartFile file
    ) {
        if(file == null) System.out.println(RESPONSE_NULL);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.createRequest(request, file)));
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<ResearchPermissionHandler>>> fetchRequests(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResearchPermissionHandler> researchPermissionHandlers = service.fetchRequests(pageable);
        PageResponse<ResearchPermissionHandler> response = new PageResponse<>(
                researchPermissionHandlers.getContent(),
                researchPermissionHandlers.getTotalElements(),
                researchPermissionHandlers.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ResearchPermissionHandler>> getById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getById(id)));
    }

    @PutMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<Response<?>> updateRequest(
            @RequestPart(name = "request") ResearchPermissionHandler request,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_UPDATE_SUCCESS, service.updateRequest(request, file)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteRequest(
            @PathVariable("id") String id
    ){
        service.deleteRequest(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
