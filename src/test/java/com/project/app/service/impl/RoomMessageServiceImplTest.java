package com.project.app.service.impl;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMessage;
import com.project.app.entity.User;
import com.project.app.repository.RoomMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RoomMessageServiceImplTest {
    @InjectMocks
    RoomMessageServiceImpl service;

    @Mock
    RoomMessageRepository repository;

    private RoomMessage roomMessage;

    @BeforeEach
    public void setup(){
        User user = new User("testing1", "jannes");
        roomMessage = new RoomMessage("id-bebas", new Room(), user, "halo guys", new Date());

        Mockito.when(repository.save(roomMessage)).thenReturn(roomMessage);
    }

    @Test
    public void create_shouldAddOneRecordtoTable(){
        List<RoomMessage> roomMessages = new ArrayList<>();
        roomMessages.add(roomMessage);
        Mockito.when(repository.findAll()).thenReturn(roomMessages);

        // expected
        RoomMessage savedRoomMessaged = service.create(roomMessage);

        assertNotNull(savedRoomMessaged.getId());
        assertEquals(1, repository.findAll().size());
    }

}