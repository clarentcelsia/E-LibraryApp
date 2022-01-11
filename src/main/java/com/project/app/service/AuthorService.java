package com.project.app.service;

import com.project.app.dto.AuthorDTO;
import com.project.app.entity.library.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    Author create(Author author);

    Author getById(String id);

    Page<Author> listWithPage(Pageable pageable, AuthorDTO authorDTO);

    String delete(String id);

}
