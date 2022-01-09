package com.project.app.service.impl;

import com.project.app.dto.ReplyDTO;
import com.project.app.entity.Post;
import com.project.app.entity.Reply;
import com.project.app.entity.User;
import com.project.app.repository.ReplyRepository;
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
class ReplyServiceImplTest {
    @InjectMocks
    ReplyServiceImpl service;

    @Mock
    ReplyRepository repository;

    @Mock
    PostServiceImpl postService;

    private Reply inputReply;
    private Reply outputReply;
    private User jannes;
    private User jono;
    @BeforeEach
    public void setup(){
        jannes = new User("user-1", "jannes");
        jono = new User("user-2", "jono");
        inputReply = new Reply(null,null, jono, "message-reply-1", new Date());
        outputReply = new Reply("reply-1",null, jono, "message-reply-1", new Date());
    }

    @Test
    public void create_Should_AddReplyToCollection_AND_ToPostCollection(){
        Post postOne = new Post("post-1", "title-1", "body-1", null,new ArrayList<>(), jannes, new Date(), new Date());
        inputReply.setPost(postOne);
        outputReply.setPost(postOne);

        Mockito.when(repository.save(inputReply)).thenReturn(outputReply);
        Mockito.when(postService.getById("post-1")).thenReturn(postOne);

        Reply savedReply = service.create(inputReply);

        List<Reply> replies = new ArrayList<>();
        replies.add(savedReply);

        // actual
        Mockito.when(repository.findAll()).thenReturn(replies);
        assertEquals(repository.findAll().size(), 1);
        assertEquals(postOne.getReply().size(), 1);
    }

    @Test
    public void getById_ShouldReturn_RequestedReply_When_IdExist(){
        Mockito.when(repository.findById("reply-1")).thenReturn(Optional.of(outputReply));

        Reply getReply = service.getById("reply-1");
        assertEquals(getReply.getId(), "reply-1");
    }

    @Test
    public void getById_ShouldThrows_ExceptionWithMessageAndNotFoundCode_when_IdNotExist(){
        Mockito.when(repository.findById("asal")).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.getById("asal"));
        assertEquals(e.getReason(), "reply with id asal not found");
        assertEquals(e.getRawStatusCode(), 404);
    }

    @Test
    public void deleteRoomById_ShouldReturn_message_AND_RemovedDeletedPostInTopicCollection(){
        Post postOne = new Post("post-1", "title-1", "body-1", null,new ArrayList<>(), jannes, new Date(), new Date());
        outputReply.setPost(postOne);
        postOne.getReply().add(outputReply);

        Mockito.when(postService.getById("post-1")).thenReturn(postOne);
        Mockito.when(repository.findById("reply-1")).thenReturn(Optional.of(outputReply));

        String message = service.deleteById("reply-1");
        assertEquals(message,"reply with id reply-1 has been deleted");
        assertEquals(postOne.getReply().size(), 0);
    }

    @Test
    public void getAll_ShouldReturn_AllPagedTopics(){
        Post postOne = new Post("post-1", "title-1", "body-1", null,new ArrayList<>(), jannes, new Date(), new Date());
        outputReply.setPost(postOne);
        postOne.getReply().add(outputReply);


        List<Reply> replies = new ArrayList<>();
        replies.add(outputReply);

        ReplyDTO dto = new ReplyDTO("message");
        Sort sort = Sort.by(Sort.Direction.ASC, "message");
        Pageable pageable = PageRequest.of(0,1, sort);

        Page<Reply> postReply = new PageImpl<>(replies, pageable, replies.size());

        // actual
        Mockito.when(repository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(postReply);

        // expected
        Page<Reply> pagedReply = service.getAll(dto, pageable);
        assertEquals(pagedReply.getTotalElements(), 1);
    }
}