package com.project.app.controller;

import com.project.app.entity.Reply;
import com.project.app.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/{replyId}")
    public Reply getReplyById(@PathVariable(value = "replyId") Integer id){
        return replyService.getById(id);
    }

    @GetMapping
    public List<Reply> getAllReply(){
        return replyService.getAll();
    }

    @PostMapping
    public Reply createReply(@RequestBody Reply reply){
        return replyService.create(reply);
    }

    @DeleteMapping("/{ReplyId}")
    public String deleteReplyById(@PathVariable(value = "ReplyId") Integer id){
        return replyService.deleteById(id);
    }
}
