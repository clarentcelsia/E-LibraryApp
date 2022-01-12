package com.project.app.controller;

import com.project.app.dto.CategoryDTO;
import com.project.app.entity.library.Category;
import com.project.app.response.Response;
import com.project.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Response<Category>> create(@RequestBody Category category) {
        Category createCategory = categoryService.createCategory(category);
        Response<Category> response = new Response<>("Data Category Has Been Created", createCategory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Response<Category>> getById(@PathVariable("categoryId") String id) {
        Category category = categoryService.getById(id);
        Response<Category> response = new Response<>(String.format("Category with id %s found", id), category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<Response<Page<Category>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "categoryName", required = false) String categoryName
    ) {
        Pageable pageable = PageRequest.of(page, size);
        CategoryDTO categoryDTO = new CategoryDTO(categoryName);
        Page<Category> categories = categoryService.listWithPage(pageable, categoryDTO);
        Response<Page<Category>> response = new Response<>("Success", categories);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Response<String>> deleteById(@PathVariable("categoryId") String id) {
        String delete = categoryService.deleteCategory(id);
        Response<String> responseDelete = new Response<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
    }

    @PutMapping
    public ResponseEntity<Response<Category>> updateById(@RequestBody Category category) {
        Category updateCategory = categoryService.updateCategory(category);
        Response<Category> response = new Response<>("Data Has Been Updated", updateCategory);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
