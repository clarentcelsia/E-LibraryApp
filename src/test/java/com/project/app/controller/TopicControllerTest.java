package com.project.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.TopicDTO;
import com.project.app.entity.Topic;
import com.project.app.entity.User;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.TopicService;
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
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc // Untuk pengetesan layer controller sebagai Client(Postman)
class TopicControllerTest {
    @Autowired
    MockMvc mockMvc; // sebagai client

    @MockBean
    TopicService topicService;

    @Autowired
    ObjectMapper objectMapper;

    private Topic requestTopic;
    private Topic outputTopic;
    private User jannes;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        requestTopic = new Topic(null, "subject-1", "subTopic-1", "deskripsi-1", jannes, null, null);
        outputTopic = new Topic("topic-1", "subject-1", "subTopic-1", "deskripsi-1", jannes, new ArrayList<>(), new Date());
    }

    @Test
    public void createTopic_shouldReturn_StatusCREATED_AND_SavedTopic_When_PostTopic() throws Exception {
        String requestJson = objectMapper.writeValueAsString(requestTopic);

        // actual
        Mockito.when(topicService.create(Mockito.any(Topic.class))).thenReturn(outputTopic);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("topic created")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputTopic.getId())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getTopicById_shouldReturn_StatusOK_and_RequestedTopic() throws Exception {
        String id = outputTopic.getId();
        String message = "getting topic";
        Mockito.when(topicService.getById(id)).thenReturn(outputTopic);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/topics/topic-1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(outputTopic.getId())));
    }

    @Test
    public void updateTopic_shouldReturn_StatusOK_AND_UpdatedTopic_When_PostTopic() throws Exception {
        Topic topicWithUpdate = new Topic("topic-1", "subject-berubah", "subTopic-1", "deskripsi-1", jannes, new ArrayList<>(), new Date());

        String requestJson = objectMapper.writeValueAsString(topicWithUpdate);

        // actual
        Mockito.when(topicService.update(Mockito.any(Topic.class))).thenReturn(topicWithUpdate);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("topic updated")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.topicSubject", Matchers.is(topicWithUpdate.getTopicSubject())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void deleteTopicById_shouldReturn_StatusOK_and_Message() throws Exception {
        String id = outputTopic.getId();
        String message = String.format("topic with id %s has been deleted", id);
        Mockito.when(topicService.deleteById(id)).thenReturn(message);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/topics/topic-1")
                .contentType(MediaType.APPLICATION_JSON);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        WebResponse<Topic> response = objectMapper.readValue(responseJson, WebResponse.class);
        assertNull(response.getData());
    }

    @Test
    public void getTopics_ShouldReturn_StatusOK_and_AllPagedTopics() throws Exception {
        // preparation all paged rooms
        Sort sort = Sort.by("topicSubject");
        Pageable pageable = PageRequest.of(0,1,sort);
        TopicDTO dto = new TopicDTO("subject", null);

        List<Topic> topics = new ArrayList<>();
        topics.add(outputTopic);
        Page<Topic> topicPage = new PageImpl<>(topics,pageable,topics.size());

        // actual
        Mockito.when(topicService.getAll(Mockito.any(TopicDTO.class), Mockito.any(Pageable.class))).thenReturn(topicPage);


        // expected
        String message =  String.format("data halaman ke 1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        "/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("size", "1")
                .queryParam("page", "0")
                .queryParam("sortBy", "topicSubject")
                .queryParam("direction", "ASC")
                .queryParam("topicSubject", "subject");


        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        PageResponse<Topic> response = objectMapper.readValue(responseJson, new TypeReference<PageResponse<Topic>>() {});

        assertNotNull(response.getData());
        assertEquals(response.getData().size(), 1);
        assertEquals(response.getTotalContent(), 1);
    }
}