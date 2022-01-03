package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.service.CategoryService;
import com.project.app.hadiyankp.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<WebResponse<Category>> createCategory(@RequestBody Category category) {
        Category createCategory = categoryService.createCategory(category);
        WebResponse<Category> response = new WebResponse<>("Data Subject Has Been Created", createCategory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<WebResponse<Category>> getCustomerById(@PathVariable("categoryId") String id) {
        Category category = categoryService.getById(id);
        WebResponse<Category> response = new WebResponse<>(String.format("Category with id %s found", id), category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
//    @GetMapping
//    public ResponseEntity<WebResponse<Page<Subject>>> listWithPage(
//            @RequestParam(name = "size", defaultValue = "2") Integer size,
//            @RequestParam(name = "page", defaultValue = "0") Integer page,
//            @RequestParam(name = "subjectName", required = false) String subjectName
//    ) {
//        Pageable pageable = PageRequest.of(page, size);
//        SubjectDTO subjectDTO = new SubjectDTO(subjectName);
//        Page<Subject> subjects = subjectService.listWithPage(pageable, subjectDTO);
//        WebResponse<Page<Subject>> response = new WebResponse<>("Success", subjects);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<WebResponse<String>> deleteSubjectById(@PathVariable("subjectId") String id) {
        String delete = categoryService.deleteCategory(id);
        WebResponse<String> responseDelete = new WebResponse<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Category>> updateCustomerById(@RequestBody Category category) {
        Category updateCategory = categoryService.updateCategory(category);
        WebResponse<Category> response = new WebResponse<>("Data Has Been Updated", updateCategory);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
