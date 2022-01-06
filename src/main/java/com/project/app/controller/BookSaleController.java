package com.project.app.controller;

import com.project.app.entity.BookSale;
import com.project.app.response.Response;
import com.project.app.service.BookSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v11/containers")
public class BookSaleController {

    @Autowired
    BookSaleService service;

    @PostMapping
    public ResponseEntity<Response<BookSale>> createItem(
            @RequestPart(name = "detail") BookSale bookSale,
            @RequestPart(name = "image") MultipartFile image,
            @RequestPart(name = "preview", required = false) MultipartFile preview,
            @RequestPart(name = "download", required = false) MultipartFile download
    ){
        Response<BookSale> response;
        if (image == null && preview == null && download == null) {
            response = new Response<>("Error: data is null!", null);
        } else if (preview == null && download == null) {
            response = new Response<>(
                    "Success: data saved successfully!",
                    service.saveIntoContainer(bookSale, image));
        } else if (preview == null) {
            response = new Response<>(
                    "Success: data saved successfully!",
                    service.saveIntoContainer(bookSale, image, download));
        } else if (download == null) {
            response = new Response<>(
                    "Success: data saved successfully!",
                    service.saveIntoContainer(bookSale, image, preview));
        } else {
            response = new Response<>(
                    "Success: data saved successfully!",
                    service.saveIntoContainer(bookSale, image, preview, download));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<BookSale>>> getItems(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data get successfully!", service.getContainers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BookSale>> getItemById(
            @PathVariable(name = "id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data getBYId successfully!", service.getContainerById(id)));
    }

    @PutMapping
    public ResponseEntity<Response<BookSale>> updateItem(
            @RequestBody BookSale container
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data updated successfully!", service.updateContainer(container)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteItem(
            @PathVariable(name = "id") String id
    ){
        service.deleteContainer(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data deleted successfully!", id));
    }
}
