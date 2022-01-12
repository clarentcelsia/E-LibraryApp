package com.project.app.service;

import com.project.app.dto.CategoryDTO;
import com.project.app.entity.library.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category createCategory(Category category);

    Category getById(String id);

    Page<Category> listWithPage(Pageable pageable, CategoryDTO categoryDTO);

    Category updateCategory(Category category);

    String deleteCategory(String id);
}
