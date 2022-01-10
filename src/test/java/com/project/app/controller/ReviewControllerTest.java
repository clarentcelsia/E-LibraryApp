package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.Book;
import com.project.app.entity.Review;
import com.project.app.entity.User;
import com.project.app.service.ReviewService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.app.util.Utility.*;
import static org.assertj.core.api.Assertions.assertThat;
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
        User user = new User("U01", "Moana");

        Review review = new Review("R01", book, user, "title", "content", 4, 3);

        mockMvc.perform(post("/api/v6/reviews")
                        .content(mapper.writeValueAsString(review))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenGetReviewsByIdSucceed_thenReturn200() throws Exception {
        Book book = new Book("B01", "Finding Nemo");
        User user = new User("U01", "Moana");
        Review review = new Review("R01", book, user, "title", "content", 4, 3);

        when(service.getReviewById(review.getReviewId())).thenReturn(review);

        mockMvc.perform(get("/api/v6/reviews/{id}", "R01")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andExpect(jsonPath("$.['data'].['reviewId']", Matchers.is(review.getReviewId())));
    }

    @Test
    public void whenReviewUpdated_thenReturn200() throws Exception {
        Book book = new Book("B01", "Finding Nemo");
        User user = new User("U01", "Moana");
        Review review = new Review("R01", book, user, "title", "content", 4, 3);

        mockMvc.perform(put("/api/v6/reviews")
                        .content(mapper.writeValueAsString(review))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_UPDATE_SUCCESS));
    }

    @Test
    public void whenReviewDeleted_thenReturn200() throws Exception {
        Book book = new Book("B01", "Finding Nemo");
        User user = new User("U01", "Moana");
        Review review = new Review("R01", book, user, "title", "content", 4, 3);

        when(service.createReview(review)).thenReturn(review);

        mockMvc.perform(delete("/api/v6/reviews/{id}", review.getReviewId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value(review.getReviewId()));
    }
}