package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.dto.CategoryDTO;
import com.project.app.hadiyankp.dto.SubjectDTO;
import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.entity.library.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category createCategory(Category category);

    Category getById(String id);

    Page<Category> listWithPage(Pageable pageable, CategoryDTO categoryDTO);

    Category updateCategory(Category category);

    String deleteCategory(String id);
}
