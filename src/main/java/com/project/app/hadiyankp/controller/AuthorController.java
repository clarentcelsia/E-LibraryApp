package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.dto.AuthorDTO;
import com.project.app.hadiyankp.entity.library.Author;
import com.project.app.hadiyankp.service.AuthorService;
import com.project.app.hadiyankp.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<WebResponse<Author>> createCategory(@RequestBody Author author) {
        Author createAuthor = authorService.createAuthor(author);
        WebResponse<Author> response = new WebResponse<>("Data Author Has Been Created", createAuthor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<WebResponse<Author>> getCustomerById(@PathVariable("authorId") String id) {
        Author author = authorService.getById(id);
        WebResponse<Author> response = new WebResponse<>(String.format("Author with id %s found", id), author);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<WebResponse<Page<Author>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "middleName", required = false) String middleName,
            @RequestParam(name = "lastName", required = false) String lastName
    ) {
        Pageable pageable =PageRequest.of(page,size);
        AuthorDTO authorDTO = new AuthorDTO(firstName,middleName,lastName);
        Page<Author> authors = authorService.listWithPage(pageable, authorDTO);
        WebResponse<Page<Author>> response = new WebResponse<>("Success", authors);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<WebResponse<String>> deleteSubjectById(@PathVariable("authorId") String id) {
        String delete = authorService.deleteAuthor(id);
        WebResponse<String> responseDelete = new WebResponse<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Author>> updateCustomerById(@RequestBody Author category) {
        Author updateAuthor = authorService.updateAuthor(category);
        WebResponse<Author> response = new WebResponse<>("Data Has Been Updated", updateAuthor);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
