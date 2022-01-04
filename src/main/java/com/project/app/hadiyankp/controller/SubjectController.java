package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.dto.SubjectDTO;
import com.project.app.hadiyankp.entity.library.Subject;
import com.project.app.hadiyankp.service.SubjectService;
import com.project.app.hadiyankp.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<WebResponse<Subject>> createSubject(@RequestBody Subject subject){
        Subject createSubject = subjectService.createSubject(subject);
        WebResponse<Subject> response = new WebResponse<>("Data Subject Has Been Created", createSubject);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<WebResponse<Subject>> getCustomerById(@PathVariable("subjectId") String id) {
        Subject subject = subjectService.getById(id);
        WebResponse<Subject> response = new WebResponse<>(String.format("Subject with id %s found",id),subject);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<WebResponse<Page<Subject>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "subjectName", required = false) String subjectName
    ) {
        Pageable pageable = PageRequest.of(page, size);
        SubjectDTO subjectDTO = new SubjectDTO(subjectName);
        Page<Subject> subjects = subjectService.listWithPage(pageable, subjectDTO);
        WebResponse<Page<Subject>> response = new WebResponse<>("Success", subjects);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<WebResponse<String>> deleteSubjectById(@PathVariable("subjectId") String id) {
        String delete = subjectService.deleteSubject(id);
        WebResponse<String>responseDelete = new WebResponse<>("Deleted",delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }


    @PutMapping
    public ResponseEntity<WebResponse<Subject>> updateCustomerById(@RequestBody Subject subject) {
        Subject updateSubject = subjectService.updateSubject(subject);
        WebResponse<Subject> response = new WebResponse<>("Data Has Been Updated",updateSubject);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
//        return customerService.updateCustomer(customer);
    }



}
