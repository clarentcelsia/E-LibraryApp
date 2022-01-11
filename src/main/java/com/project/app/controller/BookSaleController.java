package com.project.app.controller;

import com.project.app.entity.BookSale;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.BookSaleService;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;

import static com.project.app.util.Utility.*;

@RestController
@RequestMapping("/api/v11/booksale")
//@SecurityScheme(
//        in = SecuritySchemeIn.HEADER,
//        type = SecuritySchemeType.HTTP,
//        paramName = "Authorization",
//        name = "Authorization",
//        scheme = "bearer"
//)
public class BookSaleController {

    @Autowired
    BookSaleService service;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            },
            produces = "application/json"
    )
//    @Parameter(hidden = true)
    public ResponseEntity<Response<BookSale>> createItem(
            @RequestPart(name = "detail") BookSale bookSale,
            @RequestPart(name = "image") MultipartFile image,
            @RequestPart(name = "preview", required = false) MultipartFile preview,
            @RequestPart(name = "download", required = false) MultipartFile download
    ){
        Response<BookSale> response;
        if (image == null && preview == null && download == null) {
            response = new Response<>(RESPONSE_NULL, null);
        } else if (preview == null && download == null) {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    service.saveBookSale(bookSale, image));
        } else if (preview == null) {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    service.saveBookSale(bookSale, image, download));
        } else if (download == null) {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    service.saveBookSale(bookSale, image, preview));
        } else {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    service.saveBookSale(bookSale, image, preview, download));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

//    @Parameter(hidden = true)
    @GetMapping
    public ResponseEntity<Response<PageResponse<BookSale>>> getItems(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<BookSale> bookSales = service.getBookSales(pageable);
        PageResponse<BookSale> response = new PageResponse<>(
                bookSales.getContent(),
                bookSales.getTotalElements(),
                bookSales.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

//    @Parameter(hidden = true)
    @GetMapping("/{id}")
    public ResponseEntity<Response<BookSale>> getItemById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getBookSaleById(id)));
    }

    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<Response<BookSale>> updateItem(
            @RequestPart(name = "detail") BookSale bookSale,
            @RequestPart(name = "image") MultipartFile image,
            @RequestPart(name = "preview", required = false) MultipartFile preview,
            @RequestPart(name = "download", required = false) MultipartFile download
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_UPDATE_SUCCESS, service.updateWithMultipart(bookSale, image, preview, download)));
    }

//    @Parameter(hidden = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteItem(
            @PathVariable(name = "id") String id
    ){
        service.deleteBookSale(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
