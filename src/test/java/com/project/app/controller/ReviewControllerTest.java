package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.Book;
import com.project.app.entity.Review;
import com.project.app.entity.Users;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.ReviewService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReviewService service;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private ReviewController controller;

    @Test
    public void whenReviewCreated_thenReturn201() throws Exception {
        Book book = new Book("B01", "Finding Nemo");
        Users users = new Users("U01", "Moana");

        Review review = new Review("R01", book, users, "title", "content", 4, 3);

        mockMvc.perform(post("/api/v6/reviews")
                        .content(mapper.writeValueAsString(review))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Succeed: data created successfully!"));
    }

    @Test
    public void whenGetReviewsByIdSucceed_thenReturn200() throws Exception {
        Book book = new Book("B01", "Finding Nemo");
        Users users = new Users("U01", "Moana");
        Review review = new Review("R01", book, users, "title", "content", 4, 3);

        when(service.getReviewById(review.getReviewId())).thenReturn(review);

        mockMvc.perform(get("/api/v6/reviews/{id}", "R01")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Succeed: data get successfully!"))
                .andExpect(jsonPath("$.['data'].['reviewId']", Matchers.is(review.getReviewId())));
    }

    @Test
    public void whenReviewUpdated_thenReturn200() throws Exception {
        Book book = new Book("B01", "Finding Nemo");
        Users users = new Users("U01", "Moana");
        Review review = new Review("R01", book, users, "title", "content", 4, 3);

        mockMvc.perform(put("/api/v6/reviews")
                        .content(mapper.writeValueAsString(review))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Succeed: data updated successfully!"));
    }

    @Test
    public void whenReviewDeleted_thenReturn200() throws Exception {
        Book book = new Book("B01", "Finding Nemo");
        Users users = new Users("U01", "Moana");
        Review review = new Review("R01", book, users, "title", "content", 4, 3);

        when(service.createReview(review)).thenReturn(review);

        mockMvc.perform(delete("/api/v6/reviews/{id}", review.getReviewId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Succeed: data deleted successfully!"))
                .andExpect(jsonPath("$.data").value(review.getReviewId()));
    }
}