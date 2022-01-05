package com.project.app.service;

import com.project.app.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    Review createReview(Review review);

    Review updateReview(Review review);

    Page<Review> getReviews(Pageable pageable);

    Review getReviewById(String id);

    void deleteReview(String id);
}
