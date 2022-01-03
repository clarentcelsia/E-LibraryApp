package com.project.app.controller;

import com.project.app.files.Files;
import com.project.app.response.Response;
import com.project.app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(
            @PathVariable String id
    ) {
        Files file = fileService.getFile(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + file.getFilename() + "\"") //img.png
                .body(file.getData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Files>> getFileInfo(
            @PathVariable String id
    ) {
        Files file = fileService.getDataFileById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>("Success: get data successfully!", file));
    }
}
