//package com.project.app.hadiyankp.controller;
//
//import com.project.app.hadiyankp.entity.library.Genre;
//import com.project.app.hadiyankp.entity.library.Journal;
//import com.project.app.hadiyankp.entity.library.JournalAuthor;
//import com.project.app.hadiyankp.util.WebResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/journal_author")
//public class JournalAuthorController {
//    @PostMapping
//    public ResponseEntity<WebResponse<JournalAuthor>> create(
//            @RequestPart(name = "journal") JournalAuthor journalAuthor,
//            @RequestPart(name = "files")MultipartFile files){
//
//        JournalAuthor journalAuthor1 = journalAuthor.
//        WebResponse<JournalAuthor> response = new WebResponse<>("Data Genre Has Been Created", createGenre);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(response);
//    }
//}
