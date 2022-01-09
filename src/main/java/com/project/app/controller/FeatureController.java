package com.project.app.controller;

import com.project.app.entity.Features;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.app.util.Utility.*;

@RestController
@RequestMapping("/api/v8/features")
public class FeatureController {

    @Autowired
    FeatureService service;

    @PostMapping
    public ResponseEntity<Response<Features>> saveFeature(
            @RequestBody Features feature
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.saveFeature(feature)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Features>> getFeatureById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getFeatureById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Features>>> getFeatures(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        Page<Features> features = service.getFeatures(PageRequest.of(page, size));
        PageResponse<Features> response = new PageResponse<>(
                features.getContent(),
                features.getTotalElements(),
                features.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @PutMapping
    public ResponseEntity<Response<Features>> updateFeature(
            @RequestBody Features feature
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_UPDATE_SUCCESS, service.updateFeature(feature)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteClients(
            @PathVariable(name = "id") String id
    ) {
        service.deleteFeature(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
