package com.project.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.PostDTO;
import com.project.app.dto.ReplyDTO;
import com.project.app.entity.Post;
import com.project.app.entity.Reply;
import com.project.app.entity.Topic;
import com.project.app.entity.User;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.ReplyService;
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
class ReplyControllerTest {
    @Autowired
    MockMvc mockMvc; // sebagai client

    @MockBean
    ReplyService replyService;

    @Autowired
    ObjectMapper objectMapper;

    private Reply requestReply;
    private Reply outputReply;
    private Topic topicOne;
    private Post postOne;
    private User jannes;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        topicOne = new Topic("topic-1", "subject-1", "subTopic-1", "deskripsi-1", jannes, new ArrayList<>(), new Date());
        postOne = new Post("post-1", "title-1", "body-1", topicOne,new ArrayList<>(), jannes, new Date(), new Date());

        requestReply = new Reply(null, postOne, jannes, "message-reply-1", null);
        outputReply = new Reply("reply-1", postOne, jannes, "message-reply-1", new Date());
    }

    @Test
    public void createReply_shouldReturn_StatusCREATED_AND_SavedReply_WhenSendReply() throws Exception {
        String requestJson = objectMapper.writeValueAsString(requestReply);

        // actual
        Mockito.when(replyService.create(Mockito.any(Reply.class))).thenReturn(outputReply);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/replies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("reply created")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputReply.getId())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getReplyById_shouldReturn_StatusOK_and_RequestedReply() throws Exception {
        String id = outputReply.getId();
        String message = "getting reply";
        Mockito.when(replyService.getById(id)).thenReturn(outputReply);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/replies/reply-1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputReply.getId())));
    }

    @Test
    public void deletePostById_shouldReturn_StatusOK_and_Message() throws Exception {
        String id = outputReply.getId();
        String message = String.format("reply with id %s has been deleted", id);
        Mockito.when(replyService.deleteById(id)).thenReturn(message);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/replies/reply-1")
                .contentType(MediaType.APPLICATION_JSON);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        WebResponse<Reply> response = objectMapper.readValue(responseJson, WebResponse.class);
        assertNull(response.getData());
    }

    @Test
    public void getPosts_ShouldReturn_StatusOK_and_AllPagedPosts() throws Exception {
        // preparation all paged rooms
        Sort sort = Sort.by("message");
        Pageable pageable = PageRequest.of(0,1,sort);
        ReplyDTO dto = new ReplyDTO("message");

        List<Reply> replies = new ArrayList<>();
        replies.add(outputReply);
        Page<Reply> replyPage = new PageImpl<>(replies,pageable,replies.size());

        // actual
        Mockito.when(replyService.getAll(Mockito.any(ReplyDTO.class), Mockito.any(Pageable.class))).thenReturn(replyPage);


        // expected
        String message =  String.format("data halaman ke 1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        "/replies")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("size", "1")
                .queryParam("page", "0")
                .queryParam("sortBy", "message")
                .queryParam("direction", "ASC")
                .queryParam("message", "message");


        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        PageResponse<Reply> response = objectMapper.readValue(responseJson, new TypeReference<PageResponse<Reply>>() {});

        assertNotNull(response.getData());
        assertEquals(response.getData().get(0).getId(), outputReply.getId());
        assertEquals(response.getData().size(), 1);
        assertEquals(response.getTotalContent(), 1);
    }
}