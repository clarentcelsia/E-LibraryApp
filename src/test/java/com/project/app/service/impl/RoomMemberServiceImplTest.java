package com.project.app.service.impl;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.entity.User;
import com.project.app.repository.RoomMemberRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomMemberServiceImplTest {
    @InjectMocks
    RoomMemberServiceImpl service;

    @Mock
    RoomMemberRepository repository;

    private RoomMember member;

    @BeforeEach
    public void setup(){
        User user = new User("testing1", "jannes");
        member = new RoomMember("id-bebas", new Room(), user, new Date());
    }


    @Test
    public void create_shouldAddOneRecordtoTable(){
        Mockito.when(repository.save(member)).thenReturn(member);

        List<RoomMember> members = new ArrayList<>();
        members.add(member);
        Mockito.when(repository.findAll()).thenReturn(members);

        // expected
        RoomMember savedMember = service.create(member);

        assertNotNull(savedMember.getId());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void getRoomMemberById_ShouldReturn_RequestedRoomMember_when_IdExist(){
        // actual
        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(member));

        //expected
        RoomMember roomMemberById = service.getRoomMemberById("id-bebas");
        assertEquals(roomMemberById.getId(), "id-bebas");
    }

    @Test
    public void getRoomMemberById_ShouldThrow_ExceptionWithApproriateStatusCodeAndMessage_when_IdNotExist(){
        Mockito.when(repository.findById("id-asal")).thenReturn(Optional.empty());


        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> service.getRoomMemberById("id-asal"));


        assertEquals(exception.getRawStatusCode(), 404);
        assertEquals(exception.getReason(), "Member with id-asal not found");
    }

    @Test
    public void deleteRoomMemberById_Should_ReturnMessage_When_Deleted(){
        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(member));
        RoomMember member = service.getRoomMemberById("id-bebas");

        String message = service.deleteRoomMemberById("id-bebas");
        assertEquals(message, "Member with id id-bebas deleted");
    }

}