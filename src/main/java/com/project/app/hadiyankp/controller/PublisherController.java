package com.project.app.hadiyankp.controller;

import com.project.app.hadiyankp.dto.PublisherDTO;
import com.project.app.hadiyankp.entity.library.Category;
import com.project.app.hadiyankp.entity.library.Publisher;
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
@RequestMapping("/publishers")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @PostMapping
    public ResponseEntity<WebResponse<Publisher>> createPublisher(@RequestBody Publisher publisher) {
        Publisher createPublisher = publisherService.createPublisher(publisher);
        WebResponse<Publisher> response = new WebResponse<>("Data Publisher Has Been Created", createPublisher);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<WebResponse<Publisher>> getPublisherById(@PathVariable("publisherId") String id) {
        Publisher publisher = publisherService.getById(id);
        WebResponse<Publisher> response = new WebResponse<>(String.format("Publisher with id %s found", id), publisher);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<WebResponse<Page<Publisher>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "publisher", required = false) String publisher
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PublisherDTO publisherDTO = new PublisherDTO(publisher);
        Page<Publisher> publishers = publisherService.listWithPage(pageable, publisherDTO);
        WebResponse<Page<Publisher>> response = new WebResponse<>("Success", publishers);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<WebResponse<String>> deletePublisherById(@PathVariable("publisherId") String id) {
        String delete = publisherService.deletePublisher(id);
        WebResponse<String> responseDelete = new WebResponse<>("Deleted", delete);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDelete);
//        return customerService.deleteCustomer(id);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Publisher>> updatePublisherById(@RequestBody Publisher publisher) {
        Publisher updatePublisher = publisherService.updatePublisher(publisher);
        WebResponse<Publisher> response = new WebResponse<>("Data Has Been Updated", updatePublisher);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
