package com.project.app.controller;

import com.project.app.dto.AuthorDTO;
import com.project.app.entity.library.Author;
import com.project.app.response.Response;
import com.project.app.service.AuthorService;
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

@RestController
@RequestMapping({"/authors"})
@RolesAllowed("admin")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    public AuthorController() {
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Response<Author>> getById(@PathVariable("authorId") String id) {
        Author author = authorService.getById(id);
        Response<Author> response = new Response<>(String.format("Author with id %s found", id), author);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<Page<Author>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name
    ) {
        Pageable pageable = PageRequest.of(page, size);
        AuthorDTO authorDTO = new AuthorDTO(name);
        Page<Author> authors = authorService.listWithPage(pageable, authorDTO);
        Response<Page<Author>> response = new Response<>("Success", authors);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> deleteById(@PathVariable("authorId") String id) {
        String delete = authorService.delete(id);
        Response<String> responseDelete = new Response<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
    }

}
