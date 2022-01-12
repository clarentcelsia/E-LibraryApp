package com.project.app.controller;

import com.project.app.dto.ReplyDTO;
import com.project.app.entity.Reply;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ReplyService;
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
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/{replyId}")
    public ResponseEntity<Response<Reply>> getReplyById(@PathVariable(value = "replyId") String id){
        Reply savedReply = replyService.getById(id);
        Response<Reply> response = new Response<>("getting reply",savedReply);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Reply>>> getAllReply(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "message") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "message", required = false) String message
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        String info = String.format("data halaman ke %d", page+1);
        ReplyDTO dto = new ReplyDTO(message);
        Page<Reply> replyPage = replyService.getAll(dto, pageable);

        PageResponse<Reply> response = new PageResponse<>(
                replyPage.getContent(),
                replyPage.getTotalElements(),
                replyPage.getTotalPages(), page, size);

        return ResponseEntity.ok(
                new Response<>(info, response));
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
    public ResponseEntity<Response<Reply>> createReply(@RequestBody Reply reply){
        Reply savedReply = replyService.create(reply);
        Response<Reply> response = new Response<>("reply created",savedReply);
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
    @DeleteMapping("/{replyId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Response<String>> deleteReplyById(@PathVariable(value = "replyId") String id){
        String message = replyService.deleteById(id);
        Response<String> response = new Response<>(message, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
