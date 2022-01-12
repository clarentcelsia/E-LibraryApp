package com.project.app.controller;

import com.project.app.dto.TypeDTO;
import com.project.app.entity.library.Type;
import com.project.app.response.Response;
import com.project.app.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/types")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PostMapping
    public ResponseEntity<Response<Type>> createType(@RequestBody Type type){
        Type createType = typeService.createType(type);
        Response<Type>response = new Response<>("Data Type Has Been Created",createType);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<Response<Type>> getCustomerById(@PathVariable("typeId") String id) {
        Type type = typeService.getById(id);
        Response<Type> response = new Response<>(String.format("Type with id %s found",id),type);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<Response<Page<Type>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "type", required = false) String type
    ) {
        Pageable pageable = PageRequest.of(page, size);
        TypeDTO typeDTO = new TypeDTO(type);
        Page<Type> types = typeService.listWithPage(pageable, typeDTO);
        Response<Page<Type>> response = new Response<>("Success", types);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<Response<String>> deleteType(@PathVariable("typeId") String id) {
        String delete = typeService.deleteType(id);
        Response<String>responseDelete = new Response<>("Deleted",delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }
    @PutMapping
    public ResponseEntity<Response<Type>> updateById(@RequestBody Type type) {
        Type updateType = typeService.updateType(type);
        Response<Type> response = new Response<>("Data Type Has Been Updated",updateType);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
