package com.project.app.controller;

import com.project.app.dto.PostDTO;
import com.project.app.dto.ReplyDTO;
import com.project.app.entity.Post;
import com.project.app.entity.Reply;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.PostService;
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
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<WebResponse<Post>> getPostById(@PathVariable(value = "postId") String id){
        Post post = postService.getById(id);
        WebResponse<Post> response = new WebResponse<>( "getting post",post);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<Post>> getAllPost(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "title", required = false) String title
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        PostDTO dto = new PostDTO(title);
        String messageResponse = String.format("data halaman ke %d", page+1);
        Page<Post> postPage = postService.getAll(dto, pageable);

        PageResponse<Post> response = new PageResponse<>(
                postPage.getContent(),
                messageResponse,
                postPage.getTotalElements(),
                postPage.getTotalPages(), page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WebResponse<Post>> createPost(@RequestBody Post post){
        Post savedPost = postService.create(post);
        WebResponse<Post> response = new WebResponse<>( "post created",savedPost);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<WebResponse<String>> deletePostById(@PathVariable(value = "postId") String id){
        String message = postService.deleteById(id);
        WebResponse<String> response = new WebResponse<>(message, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
