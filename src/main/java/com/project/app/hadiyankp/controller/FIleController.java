package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.entity.library.Files;
import com.project.app.hadiyankp.service.impl.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FIleController {
    @Autowired
    private FileService fileService;

//
//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        Files file = fileService.ge(id);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\""
//                                + file.getName() + "\"")
//                .body(file.getData());
//    }
}
