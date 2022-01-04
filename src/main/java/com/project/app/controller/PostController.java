package com.project.app.controller;

import com.project.app.entity.Post;
import com.project.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable(value = "postId") String id){
        return postService.getById(id);
    }

    @GetMapping
    public List<Post> getAllPost(){
        return postService.getAll();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postService.create(post);
    }

    @DeleteMapping("/{postId}")
    public String deletePostById(@PathVariable(value = "postId") String id){
        return postService.deleteById(id);
    }
}
