package com.project.app.controller;

import com.project.app.dto.BookDTO;
import com.project.app.entity.Book;
import com.project.app.request.BookRequest;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.BookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.security.RolesAllowed;

import static com.project.app.utils.Utility.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PostMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE
            },
            produces = "application/json"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<Book>> create(
            @RequestPart(name = "book") BookRequest request,
            @RequestPart(name = "cover") MultipartFile cover,
            @RequestPart(name = "books") MultipartFile books
    ) {
        Book createBook = bookService.createBook(request,cover,books);
        Response<Book> response = new Response<>(RESPONSE_CREATE_SUCCESS, createBook);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Book>>> getBooks(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        BookDTO bookDTO = new BookDTO(title);

        Page<Book> books = bookService.getBooks(bookDTO, pageable);

        PageResponse<Book> response = new PageResponse<>(
                books.getContent(),
                books.getTotalElements(),
                books.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
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
    public ResponseEntity<Response<Book>> updateBook(
            @RequestPart(name = "book") BookRequest request,
            @RequestPart(name = "cover") MultipartFile cover,
            @RequestPart(name = "file") MultipartFile file
    ) {
        Book book = bookService.updateBookWithMultipart(request, cover, file);
        Response<Book> response = new Response<>(RESPONSE_UPDATE_SUCCESS, book);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Book>> getBookById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, bookService.getBookById(id)));
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
    public ResponseEntity<Response<?>> deleteBook(
            @PathVariable(name = "id") String id
    ){
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
