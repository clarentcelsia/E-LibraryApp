package com.project.app.service.impl;

import com.project.app.dto.TopicDTO;
import com.project.app.entity.Topic;
import com.project.app.entity.User;
import com.project.app.repository.PostRepository;
import com.project.app.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {
    @InjectMocks
    TopicServiceImpl service;

    @Mock
    TopicRepository repository;


    private Topic inputTopic;
    private Topic outputTopic;
    private User jannes;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        inputTopic = new Topic(null, "subject-1", "subTopic-1", "deskripsi-1", jannes, null, null);
        outputTopic = new Topic("topic-1", "subject-1", "subTopic-1", "deskripsi-1", jannes, new ArrayList<>(), new Date());
    }

    @Test
    public void create_Should_AddTopictoCollection(){
        Mockito.when(repository.save(inputTopic)).thenReturn(outputTopic);

        Topic savedTopic = service.create(inputTopic);
        List<Topic> topics = new ArrayList<>();
        topics.add(savedTopic);

        // actual
        Mockito.when(repository.findAll()).thenReturn(topics);
        assertEquals(repository.findAll().size(), 1);
    }

    @Test
    public void getById_ShouldReturn_RequestedTopic_When_IdExist(){
        Mockito.when(repository.findById("topic-1")).thenReturn(Optional.of(outputTopic));

        Topic getTopic = service.getById("topic-1");
        assertEquals(getTopic.getId(), "topic-1");
    }

    @Test
    public void getById_ShouldThrows_ExceptionWithMessageAndNotFoundCode_when_IdNotExist(){
        Mockito.when(repository.findById("asal")).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.getById("asal"));
        assertEquals(e.getReason(), "topic with id asal not found");
        assertEquals(e.getRawStatusCode(), 404);
    }

    @Test
    public void deleteRoomById_ShouldReturn_message(){
        Mockito.when(repository.findById("topic-1")).thenReturn(Optional.of(outputTopic));

        String message = service.deleteById("topic-1");
        assertEquals(message,"topic with id topic-1 has been deleted");
    }
    
    @Test
    public void updateRoom(){
        Topic requestTopic = new Topic("topic-1", "subject-berubah", "subTopic-1", "deskripsi-1", jannes, new ArrayList<>(), new Date());
        Mockito.when(repository.findById("topic-1")).thenReturn(Optional.of(outputTopic));
        Mockito.when(repository.save(requestTopic)).thenReturn(requestTopic);

        Topic updatedTopic = service.update(requestTopic);
        assertEquals(updatedTopic.getTopicSubject(), "subject-berubah");
    }

    @Test
    public void getAll_ShouldReturn_AllPagedTopics(){
        List<Topic> topics = new ArrayList<>();
        topics.add(outputTopic);

        TopicDTO dto = new TopicDTO("subject", null);
        Sort sort = Sort.by(Sort.Direction.ASC, "topicSubject");
        Pageable pageable = PageRequest.of(0,1, sort);

        Page<Topic> topicPage = new PageImpl<>(topics, pageable, topics.size());

        // actual
        Mockito.when(repository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(topicPage);

        // expected
        Page<Topic> pagedRooms = service.getAll(dto, pageable);
        assertEquals(pagedRooms.getTotalElements(), 1);
    }
}