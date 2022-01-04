package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.dto.CategoryDTO;
import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.service.CategoryService;
import com.project.app.hadiyankp.util.WebResponse;
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
    public ResponseEntity<WebResponse<Category>> create(@RequestBody Category category) {
        Category createCategory = categoryService.createCategory(category);
        WebResponse<Category> response = new WebResponse<>("Data Category Has Been Created", createCategory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<WebResponse<Category>> getById(@PathVariable("categoryId") String id) {
        Category category = categoryService.getById(id);
        WebResponse<Category> response = new WebResponse<>(String.format("Category with id %s found", id), category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<WebResponse<Page<Category>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "categoryName", required = false) String categoryName
    ) {
        Pageable pageable = PageRequest.of(page, size);
        CategoryDTO categoryDTO = new CategoryDTO(categoryName);
        Page<Category> categories = categoryService.listWithPage(pageable, categoryDTO);
        WebResponse<Page<Category>> response = new WebResponse<>("Success", categories);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<WebResponse<String>> deleteById(@PathVariable("categoryId") String id) {
        String delete = categoryService.deleteCategory(id);
        WebResponse<String> responseDelete = new WebResponse<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Category>> updateById(@RequestBody Category category) {
        Category updateCategory = categoryService.updateCategory(category);
        WebResponse<Category> response = new WebResponse<>("Data Has Been Updated", updateCategory);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
