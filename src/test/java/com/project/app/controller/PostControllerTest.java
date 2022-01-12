package com.project.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.PostDTO;
import com.project.app.dto.TopicDTO;
import com.project.app.entity.Post;
import com.project.app.entity.Topic;
import com.project.app.entity.User;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.PostService;
//import javafx.geometry.Pos;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc // Untuk pengetesan layer controller sebagai Client(Postman)
class PostControllerTest {
    @Autowired
    MockMvc mockMvc; // sebagai client

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    private Post requestPost;
    private Post outputPost;
    private Topic topicOne;
    private User jannes;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        topicOne = new Topic("topic-1", "subject-1", "subTopic-1", "deskripsi-1", jannes, new ArrayList<>(), new Date());
        requestPost = new Post(null, "title-1", "body-1", topicOne,null, jannes, null, null);
        outputPost = new Post("post-1", "title-1", "body-1", topicOne,new ArrayList<>(), jannes, new Date(), new Date());
    }

    @Test
    public void createPost_shouldReturn_StatusCREATED_AND_SavedPost_WhenSendPost() throws Exception {
        String requestJson = objectMapper.writeValueAsString(requestPost);

        // actual
        Mockito.when(postService.create(Mockito.any(Post.class))).thenReturn(outputPost);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("post created")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputPost.getId())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getPostById_shouldReturn_StatusOK_and_RequestedPost() throws Exception {
        String id = outputPost.getId();
        String message = "getting post";
        Mockito.when(postService.getById(id)).thenReturn(outputPost);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/posts/post-1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputPost.getId())));
    }

    @Test
    public void deletePostById_shouldReturn_StatusOK_and_Message() throws Exception {
        String id = outputPost.getId();
        String message = String.format("post with id %s has been deleted", id);
        Mockito.when(postService.deleteById(id)).thenReturn(message);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/posts/post-1")
                .contentType(MediaType.APPLICATION_JSON);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        WebResponse<Post> response = objectMapper.readValue(responseJson, WebResponse.class);
        assertNull(response.getData());
    }

    @Test
    public void getPosts_ShouldReturn_StatusOK_and_AllPagedPosts() throws Exception {
        // preparation all paged rooms
        Sort sort = Sort.by("title");
        Pageable pageable = PageRequest.of(0,1,sort);
        PostDTO dto = new PostDTO("title");

        List<Post> posts = new ArrayList<>();
        posts.add(outputPost);
        Page<Post> postPage = new PageImpl<>(posts,pageable,posts.size());

        // actual
        Mockito.when(postService.getAll(Mockito.any(PostDTO.class), Mockito.any(Pageable.class))).thenReturn(postPage);


        // expected
        String message =  String.format("data halaman ke 1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        "/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("size", "1")
                .queryParam("page", "0")
                .queryParam("sortBy", "title")
                .queryParam("direction", "ASC")
                .queryParam("title", "title");


        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        PageResponse<Post> response = objectMapper.readValue(responseJson, new TypeReference<PageResponse<Post>>() {});

        assertNotNull(response.getData());
        assertEquals(response.getData().size(), 1);
        assertEquals(response.getTotalContent(), 1);
    }
}