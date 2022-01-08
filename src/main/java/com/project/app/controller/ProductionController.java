package com.project.app.controller;

import com.project.app.entity.ProductionBook;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ProductionBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.project.app.util.Utility.*;

@RestController
@RequestMapping("/api/v3/productions")
public class ProductionController {

    @Autowired
    ProductionBookService productionBookService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<Response<ProductionBook>> saveProductionBook(
            @RequestPart("book") ProductionBook productionBook,
            @RequestPart(value = "image") MultipartFile image,
            @RequestPart(value = "preview", required = false) MultipartFile preview,
            @RequestPart(value = "download", required = false) MultipartFile download
    ) {
        Response<ProductionBook> response;
        if (image == null && preview == null && download == null) {
            response = new Response<>(RESPONSE_ERROR, null);
        } else if (preview == null && download == null) {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    productionBookService.save(productionBook, image));
        } else if (preview == null) {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    productionBookService.save(productionBook, image, download));
        } else if (download == null) {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    productionBookService.save(productionBook, image, preview));
        } else {
            response = new Response<>(
                    RESPONSE_CREATE_SUCCESS,
                    productionBookService.save(productionBook, image, preview, download));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<ProductionBook>>> getAllProductionBooks(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_GET_SUCCESS,
                        productionBookService.getAll(pageable)
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductionBook>> getProductionBookById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_GET_SUCCESS,
                        productionBookService.getById(id)));
    }

    @PutMapping
    public ResponseEntity<Response<ProductionBook>> updateProductionBook(
            @RequestPart(name = "book") ProductionBook productionBook,
            @RequestPart(value = "image") MultipartFile image,
            @RequestPart(value = "preview", required = false) MultipartFile preview,
            @RequestPart("download") MultipartFile download

    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_UPDATE_SUCCESS,
                        productionBookService.updateWithMultipart(productionBook, image, preview, download)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteProductionBook(
            @PathVariable(name = "id") String id
    ) {
        productionBookService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        RESPONSE_DELETE_SUCCESS, id));
    }
}
