package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.AuthorDTO;
import com.project.app.hadiyankp.dto.CategoryDTO;
import com.project.app.hadiyankp.entity.library.Author;
import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.AuthorRepository;
import com.project.app.hadiyankp.service.AuthorService;
import com.project.app.hadiyankp.specification.AuthorSpecification;
import com.project.app.hadiyankp.specification.CategorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getById(String id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        }else {
            throw new NotFoundException(String.format("Author with id %s not found", id));
        }
    }

    @Override
    public Page<Author> listWithPage(Pageable pageable, AuthorDTO authorDTO) {
        Specification<Author> specification = AuthorSpecification.getSpecification(authorDTO);
        return authorRepository.findAll(specification,pageable);
    }

    @Override
    public Author update(Author author) {
        getById(author.getId());
        return authorRepository.save(author);
    }

    @Override
    public String delete(String id) {
        Author author = getById(id);
        authorRepository.delete(author);
        return String.format("Category with Id %s has been deleted",author.getId());
    }
}
