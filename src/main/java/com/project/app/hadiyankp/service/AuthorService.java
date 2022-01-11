package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.dto.AuthorDTO;
import com.project.app.hadiyankp.dto.CategoryDTO;
import com.project.app.hadiyankp.entity.library.Author;
import com.project.app.hadiyankp.entity.library.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    Author create(Author author);

    Author getById(String id);

    Page<Author> listWithPage(Pageable pageable, AuthorDTO authorDTO);

    String delete(String id);

}
