package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.CategoryDTO;
import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.CategoryRepository;
import com.project.app.hadiyankp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }else {
            throw new NotFoundException(String.format("Category with id %s not found", id));
        }
    }

    @Override
    public Page<Category> listWithPage(Pageable pageable, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        getById(category.getId());
        return categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(String id) {
        Category category = getById(id);
        categoryRepository.delete(category);
        return String.format("Category with Id %s has been deleted",category.getId());
    }
}
