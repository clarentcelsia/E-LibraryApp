package com.project.app.controller;

import com.project.app.entity.Review;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ReviewService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static com.project.app.utils.Utility.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewService service;

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
    public ResponseEntity<Response<Review>> createReview(
            @RequestBody Review review
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.createReview(review)));
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
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Review>> getReviewById(
            @PathVariable("id") String id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getReviewById(id)));
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
    public ResponseEntity<Response<Review>> updateReview(
            @RequestBody Review review
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_UPDATE_SUCCESS, service.updateReview(review)));
    }

    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    paramType = "header",
                    required = true,
                    dataType = "string"
            ))
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Response<String>> deleteReview(
            @PathVariable(name = "id") String id
    ){
        service.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
