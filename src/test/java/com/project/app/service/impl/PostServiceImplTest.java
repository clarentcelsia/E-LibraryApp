package com.project.app.service.impl;

import com.project.app.dto.PostDTO;
import com.project.app.entity.Post;
import com.project.app.entity.Topic;
import com.project.app.entity.User;
import com.project.app.repository.PostRepository;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    PostServiceImpl service;

    @Mock
    PostRepository repository;

    @Mock
    TopicServiceImpl topicService;

    private Post inputPost;
    private Post outputPost;
    private Topic topicOne;
    private User jannes;
    private User jono;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        jono = new User("user-2", "jono");
        topicOne = new Topic("topic-1", "subject-1", "subTopic-1", "deskripsi-1", jannes, new ArrayList<>(), new Date());
        inputPost = new Post(null, "title-1", "body-1", topicOne,null, jannes, null, null);
        outputPost = new Post("post-1", "title-1", "body-1", topicOne,new ArrayList<>(), jannes, new Date(), new Date());
    }

    @Test
    public void create_Should_AddPostToCollection_AND_ToTopicCollection(){
        Mockito.when(repository.save(inputPost)).thenReturn(outputPost);
        Mockito.when(topicService.getById("topic-1")).thenReturn(topicOne);

        Post savedPost = service.create(inputPost);

        List<Post> posts = new ArrayList<>();
        posts.add(savedPost);

        // actual
        Mockito.when(repository.findAll()).thenReturn(posts);
        assertEquals(repository.findAll().size(), 1);
        assertEquals(topicOne.getPosts().size(), 1);
    }

    @Test
    public void getById_ShouldReturn_RequestedPost_When_IdExist(){
        Mockito.when(repository.findById("post-1")).thenReturn(Optional.of(outputPost));

        Post getPost = service.getById("post-1");
        assertEquals(getPost.getId(), "post-1");
    }

    @Test
    public void getById_ShouldThrows_ExceptionWithMessageAndNotFoundCode_when_IdNotExist(){
        Mockito.when(repository.findById("asal")).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.getById("asal"));
        assertEquals(e.getReason(), "post with id asal not found");
        assertEquals(e.getRawStatusCode(), 404);
    }

    @Test
    public void deleteRoomById_ShouldReturn_message_AND_RemovedDeletedPostInTopicCollection(){
        // make new topic so it wont depend on topic one as it is already used in create post test
        Topic topicTwo = new Topic("topic-2", "subject-2", "subTopic-2", "deskripsi-2", jannes, new ArrayList<>(), new Date());
        topicTwo.getPosts().add(outputPost);
        outputPost.setTopic(topicTwo);

        Mockito.when(topicService.getById("topic-2")).thenReturn(topicTwo);
        Mockito.when(repository.findById("post-1")).thenReturn(Optional.of(outputPost));

        String message = service.deleteById("post-1");
        assertEquals(message,"post with id post-1 has been deleted");
        assertEquals(topicTwo.getPosts().size(), 0);
    }

    @Test
    public void getAll_ShouldReturn_AllPagedTopics(){
        outputPost.setTopic(topicOne);
        List<Post> posts = new ArrayList<>();
        posts.add(outputPost);

        PostDTO dto = new PostDTO("title");
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        Pageable pageable = PageRequest.of(0,1, sort);

        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        // actual
        Mockito.when(repository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(postPage);

        // expected
        Page<Post> pagedPost = service.getAll(dto, pageable);
        assertEquals(pagedPost.getTotalElements(), 1);
    }

}