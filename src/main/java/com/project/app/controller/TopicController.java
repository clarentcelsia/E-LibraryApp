package com.project.app.controller;

import com.project.app.dto.TopicDTO;
import com.project.app.entity.Topic;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.TopicService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/{topicId}")
    public ResponseEntity<Response<Topic>> getTopicById(@PathVariable(value = "topicId") String id){
        Topic topic = topicService.getById(id);
        Response<Topic> response = new Response<>( "getting topic",topic);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Topic>>> getAllTopic(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "topicSubject") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "topicSubject", required = false) String topicSubject,
            @RequestParam(name = "userName", required = false) String userName

    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        String message = String.format("data halaman ke %d", page+1);
        TopicDTO dto = new TopicDTO(topicSubject, userName);
        Page<Topic> pagedTopic = topicService.getAll(dto, pageable);

        PageResponse<Topic> response = new PageResponse<>(
                pagedTopic.getContent(),
                pagedTopic.getTotalElements(),
                pagedTopic.getTotalPages(), page, size);

        return ResponseEntity.ok(
                new Response<>(message, response));
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Response<Topic>> createTopic(@RequestBody Topic topic){
        Topic savedTopic = topicService.create(topic);
        Response<Topic> response = new Response<>("topic created",savedTopic);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Response<Topic>> updateTopic(@RequestBody Topic topic){
        Topic savedTopic = topicService.update(topic);
        Response<Topic> response = new Response<>( "topic updated",savedTopic);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @DeleteMapping("/{topicId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Response<String>> deleteTopicById(@PathVariable(value = "topicId") String id){
        String message = topicService.deleteById(id);
        Response<String> response = new Response<>(message, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
