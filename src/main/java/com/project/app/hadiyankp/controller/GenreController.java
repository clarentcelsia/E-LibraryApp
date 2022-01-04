package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.dto.GenreDTO;
import com.project.app.hadiyankp.entity.library.Genre;
import com.project.app.hadiyankp.entity.library.Publisher;
import com.project.app.hadiyankp.service.GenreService;
import com.project.app.hadiyankp.service.PublisherService;
import com.project.app.hadiyankp.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<WebResponse<Genre>> create(@RequestBody Genre genre) {
        Genre createGenre = genreService.create(genre);
        WebResponse<Genre> response = new WebResponse<>("Data Genre Has Been Created", createGenre);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<WebResponse<Genre>> getById(@PathVariable("genreId") String id) {
        Genre genre = genreService.getById(id);
        WebResponse<Genre> response = new WebResponse<>(String.format("Genre with id %s found", id), genre);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<WebResponse<Page<Genre>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "genre", required = false) String genre
    ) {
        Pageable pageable = PageRequest.of(page, size);
        GenreDTO genreDTO = new GenreDTO(genre);
        Page<Genre> genres = genreService.listWithPage(pageable, genreDTO);
        WebResponse<Page<Genre>> response = new WebResponse<>("Success", genres);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<WebResponse<String>> deleteById(@PathVariable("genreId") String id) {
        String delete = genreService.delete(id);
        WebResponse<String> responseDelete = new WebResponse<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Genre>> updateById(@RequestBody Genre genre) {
        Genre updateGenre = genreService.update(genre);
        WebResponse<Genre> response = new WebResponse<>("Data Has Been Updated", updateGenre);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
