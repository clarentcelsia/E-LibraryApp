package com.project.app.controller;

import com.project.app.entity.Topic;
import com.project.app.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/{topicId}")
    public Topic getTopicById(@PathVariable(value = "topicId") String id){
        return topicService.getById(id);
    }

    @GetMapping
    public List<Topic> getAllTopic(){
        return topicService.getAll();
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic){
        return topicService.create(topic);
    }

    @PutMapping
    public Topic updateTopic(@RequestBody Topic topic){
        return topicService.create(topic);
    }


    @DeleteMapping("/{topicId}")
    public String deleteTopicById(@PathVariable(value = "topicId") String id){
        return topicService.deleteById(id);
    }
}
