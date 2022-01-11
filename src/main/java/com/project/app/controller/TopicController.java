package com.project.app.controller;

import com.project.app.dto.TopicDTO;
import com.project.app.entity.Topic;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/{topicId}")
    public ResponseEntity<WebResponse<Topic>> getTopicById(@PathVariable(value = "topicId") String id){
        Topic topic = topicService.getById(id);
        WebResponse<Topic> response = new WebResponse<>( "getting topic",topic);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<Topic>> getAllTopic(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "topicSubject") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "topicSubject", required = false) String topicSubject,
            @RequestParam(name = "userName", required = false) String userName

    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        TopicDTO dto = new TopicDTO(topicSubject, userName);
        String message = String.format("data halaman ke %d", page+1);
        Page<Topic> pagedTopic = topicService.getAll(dto, pageable);

        PageResponse<Topic> response = new PageResponse<>(
                pagedTopic.getContent(),
                message,
                pagedTopic.getTotalElements(),
                pagedTopic.getTotalPages(), page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WebResponse<Topic>> createTopic(@RequestBody Topic topic){
        Topic savedTopic = topicService.create(topic);
        WebResponse<Topic> response = new WebResponse<>("topic created",savedTopic);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Topic>> updateTopic(@RequestBody Topic topic){
        Topic savedTopic = topicService.update(topic);
        WebResponse<Topic> response = new WebResponse<>( "topic updated",savedTopic);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<WebResponse<String>> deleteTopicById(@PathVariable(value = "topicId") String id){
        String message = topicService.deleteById(id);
        WebResponse<String> response = new WebResponse<>(message, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
