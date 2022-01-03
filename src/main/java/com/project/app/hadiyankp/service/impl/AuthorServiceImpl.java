package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.AuthorDTO;
import com.project.app.hadiyankp.entity.library.Author;
import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.AuthorRepository;
import com.project.app.hadiyankp.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Override
    public Author createAuthor(Author author) {
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
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        getById(author.getId());
        return authorRepository.save(author);
    }

    @Override
    public String deleteAuthor(String id) {
        Author author = getById(id);
        authorRepository.delete(author);
        return String.format("Author with Id %s has been deleted",author.getId());
    }
}
