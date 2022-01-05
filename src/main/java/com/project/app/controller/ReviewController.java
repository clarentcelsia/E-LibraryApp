package com.project.app.controller;

import com.project.app.entity.Review;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/reviews")
public class ReviewController {

    @Autowired
    ReviewService service;

    @PostMapping
    public ResponseEntity<Response<Review>> createReview(
            @RequestBody Review review
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>("Succeed: data created successfully!", service.createReview(review)));
    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Review>>> getReviews(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = service.getReviews(pageable);
        PageResponse<Review> response = new PageResponse<>(
                reviews.getContent(),
                reviews.getTotalElements(),
                reviews.getTotalPages(),
                page,
                size
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data get successfully!", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Review>> getReviewById(
            @PathVariable("id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data get successfully!", service.getReviewById(id)));
    }

    @PutMapping
    public ResponseEntity<Response<Review>> updateReview(
            @RequestBody Review review
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data updated successfully!", service.updateReview(review)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> updateReview(
            @PathVariable(name = "id") String id
    ){
        service.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: data deleted successfully!", id));
    }
}
