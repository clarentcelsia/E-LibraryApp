package com.project.app.controller;

import com.project.app.dto.BookDTO;
import com.project.app.entity.Book;
import com.project.app.request.BookRequest;
import com.project.app.service.BookService;
import com.project.app.utils.PageResponse;
import com.project.app.utils.WebResponse;
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
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<WebResponse<Book>> create(
            @RequestPart(name = "book") BookRequest request,
            @RequestPart(name = "cover") MultipartFile cover,
            @RequestPart(name = "books") MultipartFile books
    ) {
        Book createBook = bookService.createBook(request,cover,books);
        WebResponse<Book> response = new WebResponse<>(RESPONSE_CREATE_SUCCESS, createBook);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Book>>> getBooks(
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
                .body(new WebResponse<>(RESPONSE_GET_SUCCESS, response));
    }

    @PutMapping
    public ResponseEntity<WebResponse<Book>> updateBook(
            @RequestPart(name = "book") BookRequest request,
            @RequestPart(name = "cover") MultipartFile cover,
            @RequestPart(name = "file") MultipartFile file
    ) {
        Book book = bookService.updateBookWithMultipart(request, cover, file);
        WebResponse<Book> response = new WebResponse<>(RESPONSE_UPDATE_SUCCESS, book);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<Book>> getBookById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>(RESPONSE_GET_SUCCESS, bookService.getBookById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> deleteBook(
            @PathVariable(name = "id") String id
    ){
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
