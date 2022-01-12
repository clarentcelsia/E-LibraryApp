package com.project.app.service.impl;

import com.project.app.dto.AuthorDTO;
import com.project.app.entity.library.Author;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.AuthorRepository;
import com.project.app.service.AuthorService;
import com.project.app.specification.AuthorSpecification;
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
            throw new ResourceNotFoundException(String.format("Author with id %s not found", id));
        }
    }

    @Override
    public Page<Author> listWithPage(Pageable pageable, AuthorDTO authorDTO) {
        Specification<Author> specification = AuthorSpecification.getSpecification(authorDTO);
        return authorRepository.findAll(specification,pageable);
    }

    @Override
    public String delete(String id) {
        Author author = getById(id);
        authorRepository.delete(author);
        return String.format("Category with Id %s has been deleted",author.getId());
    }
}
