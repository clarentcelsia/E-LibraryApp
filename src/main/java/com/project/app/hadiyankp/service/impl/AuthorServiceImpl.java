package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.AuthorDTO;
import com.project.app.hadiyankp.entity.library.Author;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.AuthorRepository;
import com.project.app.hadiyankp.service.AuthorService;
import com.project.app.hadiyankp.specification.AuthorSpecification;
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

    public AuthorServiceImpl() {
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getById(String id) {
        return this.findByIdOrThrowNotFound(id);
    }

    @Override
    public Author getActiveAuthor(String id) {
        return authorRepository.getActiveCustomer(id).orElseThrow(() -> new NotFoundException("Author Not Found"));
    }

    @Override
    public Page<Author> listWithPage(Pageable pageable, AuthorDTO authorDTO) {
        Specification<Author> specification = AuthorSpecification.getSpecification(authorDTO);
        return authorRepository.findAll(specification, pageable);
    }

    @Override
    public Author updateAuthor(Author author) {
        findByIdOrThrowNotFound(author.getId());
        return authorRepository.save(author);
    }

    @Override
    public String deleteAuthor(String id) {
        Author author = this.findByIdOrThrowNotFound(id);
        if (author.getDeleted()) {
            throw new NotFoundException("Customer Not Found");
        }
        author.setDeleted(true);
        authorRepository.save(author);
        return String.format("Author with Id %s has been deleted", author.getId());
    }

    private Author findByIdOrThrowNotFound(String id) {
        Optional<Author> author = this.authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        } else {
            throw new NotFoundException("Customer tidak ditemukan");
        }
    }
}
