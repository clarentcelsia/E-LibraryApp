package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.dto.JournalDTO;
import com.project.app.hadiyankp.entity.library.Journal;
import com.project.app.hadiyankp.request.JournalRequest;
import com.project.app.hadiyankp.response.JournalResponse;
import com.project.app.hadiyankp.service.JournalService;
import com.project.app.hadiyankp.service.impl.FileService;
import com.project.app.hadiyankp.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalService journalService;

    @Autowired
    FileService fileService;

    @PostMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<WebResponse<Journal>> create(
            @RequestPart(name = "journal") JournalRequest journal,
            @RequestPart(name = "files") MultipartFile photo
    ) {
        Journal createJournal = journalService.create(journal,photo);
        WebResponse<Journal> response = new WebResponse<>("Data Journal Has Been Created", createJournal);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{journalId}")
    public ResponseEntity<WebResponse<Journal>> getById(@PathVariable("journalId") String id) {
        Journal journal = journalService.getById(id);
        WebResponse<Journal> response = new WebResponse<>(String.format("Journal with id %s found", id), journal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<WebResponse<Page<Journal>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "publishDate", required = false) String publishDate
    ) {
        Pageable pageable = PageRequest.of(page, size);
        JournalDTO journalDTO = new JournalDTO(title,publishDate);
        Page<Journal> journals = journalService.listWithPage(pageable, journalDTO);
        WebResponse<Page<Journal>> response = new WebResponse<>("Success", journals);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{journalId}")
    public ResponseEntity<WebResponse<String>> deleteJournalById(@PathVariable("journalId") String id) {
        String delete = journalService.deleteById(id);
        WebResponse<String> responseDelete = new WebResponse<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
    }

    @PutMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE
            },
            produces = "application/json"
    )
    public ResponseEntity<WebResponse<Journal>> update(
            @RequestPart(name = "journal") JournalRequest journal,
            @RequestPart(name = "files") MultipartFile photo
    ) {
        Journal updateById = journalService.updateById(journal,photo);
        WebResponse<Journal> response = new WebResponse<>("Data Journal Has Been Updated", updateById);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }




}
