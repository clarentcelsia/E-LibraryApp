package com.project.app.controller;

import com.project.app.handler.ProductionRequestHandler;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ProductionRequestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static com.project.app.utils.Utility.*;

@RestController
@RequestMapping("/productions/request")
public class ProductionRequestController {

    @Autowired
    ProductionRequestService service;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Response<ProductionRequestHandler>> createRequest(
            @RequestBody ProductionRequestHandler requestHandler
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.createRequest(requestHandler)));
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<ProductionRequestHandler>>> fetchRequests(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductionRequestHandler> productionRequestHandlers = service.fetchRequests(pageable);
        PageResponse<ProductionRequestHandler> response = new PageResponse<>(
                productionRequestHandlers.getContent(),
                productionRequestHandlers.getTotalElements(),
                productionRequestHandlers.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductionRequestHandler>> getRequestById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getById(id)));
    }

    @GetMapping("/find")
    public ResponseEntity<Response<List<ProductionRequestHandler>>> findRequestByStatus(
            @RequestParam(name = "status", defaultValue = "true") String status
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.findRequestByStatus(Boolean.valueOf(status.toLowerCase()))));
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<ProductionRequestHandler>> updateRequest(
            @RequestBody ProductionRequestHandler requestHandler
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_UPDATE_SUCCESS, service.updateRequest(requestHandler)));
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<?>> deleteRequest(
            @PathVariable(name = "id") String id
    ){
        service.deleteRequest(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }

}
