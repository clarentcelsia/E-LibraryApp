package com.project.app.service.impl;

import com.project.app.entity.Book;
import com.project.app.entity.Review;
import com.project.app.entity.User;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl service;

    @Mock
    private ReviewRepository repository;

    @Autowired
    MockMvc mockMvc;

    @Mock
    Pageable pageable;

    private Review review;
    private Review output;

    @BeforeEach
    void setUp() {
        Book book = new Book();
        book.setId("b01");
        book.setTitle("title");
        review = new Review();
        review.setReviewId("r01");
        review.setBook(book);
        review.setUsers(new User("u01", "name"));
        review.setTitle("comment title");
        review.setContent("content");
        review.setRating(4);
        review.setAgree(2);

        output = new Review(review.getReviewId(), review.getBook(), review.getUsers(),
                review.getTitle(), review.getContent(), review.getRating(), review.getAgree());

    }

    @Test
    public void whenGivenReviewSucceed_thenReturnReview() {
        service.createReview(review);

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        when(repository.findAll()).thenReturn(reviewList);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void whenGivenReviewNull_thenReturn0(){
        service.createReview(null);
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void whenReviewGetSucceed_thenReturnReviews() {

        Page<Review> reviews = mock(Page.class);

        when(repository.findAll(pageable)).thenReturn(reviews);

        Page<Review> serviceReviews = service.getReviews(pageable);

        assertEquals(reviews, serviceReviews);
    }

    @Test
    public void whenReviewGetByIdSucceed_thenReturnNotNull() {
        given(repository.findById(review.getReviewId())).willReturn(Optional.of(review));

        Review reviewById = service.getReviewById(review.getReviewId());

        assertThat(reviewById).isNotNull();
    }

    @Test
    public void whenReviewGetByIdNotFound_thenReturnException() throws Exception{
        service.createReview(review);

        String id = "AC123";

        assertThrows(ResourceNotFoundException.class, ()->{
            service.getReviewById(id);
        });

    }

    @Test
    public void whenReviewUpdated_thenVerifyRepoSaveUpdatedReview(){
        given(repository.save(any(Review.class))).willReturn(review);

        whenReviewGetByIdSucceed_thenReturnNotNull();

        Review review = service.updateReview(this.review);

        assertThat(review).isNotNull();

        verify(repository).save(any(Review.class));
    }

    @Test
    public void whenReviewDeleted_thenDeletedSuccess(){
        whenReviewGetByIdSucceed_thenReturnNotNull();

        service.deleteReview(review.getReviewId());
        service.deleteReview(review.getReviewId());

        verify(repository, times(2)).delete(review);
    }
}