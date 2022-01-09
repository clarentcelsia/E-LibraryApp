package com.project.app.controller;

import com.project.app.dto.ReplyDTO;
import com.project.app.dto.TopicDTO;
import com.project.app.entity.Reply;
import com.project.app.entity.Topic;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.ReplyService;
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
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/{replyId}")
    public ResponseEntity<WebResponse<Reply>> getReplyById(@PathVariable(value = "replyId") Integer id){
        Reply savedReply = replyService.getById(id);
        WebResponse<Reply> response = new WebResponse<>(savedReply, "reply created");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<Reply>> getAllReply(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "message") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "message", required = false) String message
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        ReplyDTO dto = new ReplyDTO(message);
        String messageResponse = String.format("data halaman ke %d", page+1);
        Page<Reply> replyPage = replyService.getAll(dto, pageable);

        PageResponse<Reply> response = new PageResponse<>(
                replyPage.getContent(),
                messageResponse,
                replyPage.getTotalElements(),
                replyPage.getTotalPages(), page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WebResponse<Reply>> createReply(@RequestBody Reply reply){
        Reply savedReply = replyService.create(reply);
        WebResponse<Reply> response = new WebResponse<>(savedReply, "reply created");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/{ReplyId}")
    public ResponseEntity<WebResponse<String>> deleteReplyById(@PathVariable(value = "replyId") Integer id){
        String message = replyService.deleteById(id);
        WebResponse<String> response = new WebResponse<>(message, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
