package com.project.app.service.impl;


import com.project.app.entity.Review;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.ReviewRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.app.util.Utility.RESPONSE_NOT_FOUND;


@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository repository;

    @Override
    public Review createReview(Review review) {
        if(review == null) return null;
        if(review.getBook().getBookId() == null)
            System.out.println("which book you want to make a review");
        return repository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        getReviewById(review.getReviewId());
        return createReview(review);
    }

    @Override
    public Page<Review> getReviews(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Review getReviewById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(RESPONSE_NOT_FOUND, id)));
    }

    @Override
    public void deleteReview(String id) {
        Review review = getReviewById(id);
        repository.delete(review);
    }
}
