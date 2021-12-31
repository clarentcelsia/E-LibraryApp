package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.dto.TypeDTO;
import com.project.app.hadiyankp.entity.library.Type;
import com.project.app.hadiyankp.service.TypeService;
import com.project.app.hadiyankp.util.WebResponse;
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
    public ResponseEntity<WebResponse<Type>> createType(@RequestBody Type type){
        Type createType = typeService.createType(type);
        WebResponse<Type>response = new WebResponse<>("Data Type Has Been Created",createType);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<WebResponse<Type>> getCustomerById(@PathVariable("typeId") String id) {
        Type type = typeService.getById(id);
        WebResponse<Type> response = new WebResponse<>(String.format("Type with id %s found",id),type);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<WebResponse<Page<Type>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "type", required = false) String type
    ) {
        Pageable pageable = PageRequest.of(page, size);
        TypeDTO typeDTO = new TypeDTO(type);
        Page<Type> types = typeService.listWithPage(pageable, typeDTO);
        WebResponse<Page<Type>> response = new WebResponse<>("Success", types);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<WebResponse<String>> deleteType(@PathVariable("typeId") String id) {
        String delete = typeService.deleteType(id);
        WebResponse<String>responseDelete = new WebResponse<>("Deleted",delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }
    @PutMapping
    public ResponseEntity<WebResponse<Type>> updateCustomerById(@RequestBody Type type) {
        Type updateType = typeService.updateType(type);
        WebResponse<Type> response = new WebResponse<>("Data Type Has Been Updated",updateType);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
//        return customerService.updateCustomer(customer);
    }
}
