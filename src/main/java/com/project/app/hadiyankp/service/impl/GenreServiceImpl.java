package com.project.app.hadiyankp.service.impl;

import com.project.app.hadiyankp.dto.GenreDTO;
import com.project.app.hadiyankp.entity.library.Genre;
import com.project.app.hadiyankp.exception.NotFoundException;
import com.project.app.hadiyankp.repository.GenreRepository;
import com.project.app.hadiyankp.service.GenreService;
import com.project.app.hadiyankp.specification.GenreSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    GenreRepository genreRepository;

    @Override
    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre getById(String id) {
        Optional<Genre>genre = genreRepository.findById(id);
        if (genre.isPresent()){
            return genre.get();
        }else {
            throw new NotFoundException(String.format("Genre with id %s not found", id));
        }
    }

    @Override
    public Page<Genre> listWithPage(Pageable pageable, GenreDTO genreDTO) {
        Specification<Genre> specification = GenreSpecification.getSpecification(genreDTO);
        return genreRepository.findAll(specification,pageable);
    }

    @Override
    public Genre update(Genre genre) {
        getById(genre.getId());
        return genreRepository.save(genre);
    }

    @Override
    public String delete(String id) {
        Genre genre = getById(id);
        genreRepository.delete(genre);
        return String.format("Genre with Id %s has been deleted",genre.getId());
    }
}
