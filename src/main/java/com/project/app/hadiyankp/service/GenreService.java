package com.project.app.hadiyankp.service;

import com.project.app.hadiyankp.dto.GenreDTO;
import com.project.app.hadiyankp.entity.library.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {
    Genre create(Genre genre);

    Genre getById(String id);

    Page<Genre> listWithPage(Pageable pageable, GenreDTO genreDTO);

    Genre update(Genre genre);

    String delete(String id);
}
