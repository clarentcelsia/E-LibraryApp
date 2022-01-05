package com.project.app.controller;

import com.project.app.entity.Features;
import com.project.app.response.Response;
import com.project.app.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .body(new Response<>("Succeed: client saved successfully!", service.saveFeature(feature)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Features>> getFeatureById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: client saved successfully!", service.getFeatureById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<List<Features>>> getFeatures() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: get client successfully!", service.getFeatures()));
    }

    @PutMapping
    public ResponseEntity<Response<Features>> updateFeature(
            @RequestBody Features feature
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: update client successfully!", service.updateFeature(feature)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> getClients(
            @PathVariable(name = "id") String id
    ) {
        service.deleteFeature(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: delete client successfully!", id));
    }
}
