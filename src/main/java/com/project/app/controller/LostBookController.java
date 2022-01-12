package com.project.app.controller;

import com.project.app.dto.LostBookDTO;
import com.project.app.entity.LostBookReport;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.LostBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lost-books")
public class LostBookController {
    @Autowired
    private LostBookService service;

    @GetMapping
    public ResponseEntity<PageResponse<LostBookReport>> getAllLostBook(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "dateLost") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "bookId", required = false) String bookId,
            @RequestParam(name = "dateLost", required = false) String dateLost
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        String message = String.format("data halaman ke %d", page+1);
        LostBookDTO dto = new LostBookDTO(bookId , dateLost);
        Page<LostBookReport> pagedLost = service.getAll(dto, pageable);

        PageResponse<LostBookReport> response = new PageResponse<>(
                pagedLost.getContent(), message,
                pagedLost.getTotalElements(), pagedLost.getTotalPages(),
                page+1 , size
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
