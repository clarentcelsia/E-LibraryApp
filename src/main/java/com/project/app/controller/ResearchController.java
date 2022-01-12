package com.project.app.controller;

import com.project.app.entity.Research;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.app.utils.Utility.*;

@RestController
@RequestMapping("/research")
public class ResearchController {

    @Autowired
    ResearchService service;

    @PostMapping
    public ResponseEntity<Response<Research>> saveResearch(
            @RequestBody Research research
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.saveResearch(research)));
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Research>>> getResearch(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Research> research = service.getResearch(pageable);
        PageResponse<Research> response = new PageResponse<>(
                research.getContent(),
                research.getTotalElements(),
                research.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Research>> getById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteResearch(
            @PathVariable("id") String id
    ){
        service.deleteResearch(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
