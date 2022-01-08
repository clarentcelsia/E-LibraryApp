package com.project.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.entity.User;
import com.project.app.response.WebResponse;
import com.project.app.service.RoomService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc // Untuk pengetesan layer controller sebagai Client(Postman)
class RoomControllerTest {

    @Autowired
    MockMvc mockMvc; // sebagai client

    @MockBean
    RoomService roomService;

    @Autowired
    ObjectMapper objectMapper;

    private User user;
    private Room savedRoom;

    @BeforeEach
    void setUp() {
        user = new User("testing1", "jannes");
        savedRoom = new Room("uuid-bebas", "bebas", "bebas", new Date(), user, new HashSet<>(), new ArrayList<>());

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createRoom_shouldReturn_StatusCREATED_AND_SavedROOM_When_PostRoom() throws Exception {
        Room requestRoom = new Room(null, "bebas", "bebas", null, user, null, null );
        String requestJson = objectMapper.writeValueAsString(requestRoom);

        // actual
        Mockito.when(roomService.create(Mockito.any(Room.class))).thenReturn(savedRoom);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("room created")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(savedRoom.getId())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getRoomById_shouldReturn_StatusOK_and_RequestedRoom() throws Exception {
        String id = savedRoom.getId();
        String message = String.format("room with id %s found", id);
        Mockito.when(roomService.getRoomById(id)).thenReturn(savedRoom);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/rooms/uuid-bebas")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.is(savedRoom.getId())));
    }

    @Test
    public void deleteRoomById_shouldReturn_StatusOK_and_Message() throws Exception {
        String id = savedRoom.getId();
        String message = String.format("room with id %s deleted", id);
        Mockito.when(roomService.deleteRoomById(id)).thenReturn(message);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/rooms/uuid-bebas")
                .contentType(MediaType.APPLICATION_JSON);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        WebResponse<Room> response = objectMapper.readValue(responseJson, WebResponse.class);
        assertNull(response.getData());
    }

    @Test
    public void addMemberToRoom_shouldReturn_StatusCREATED_and_RoomWithAddedMembers_When_PostRoomMember() throws Exception {
        RoomMember roomMember = new RoomMember("member-id", savedRoom , user, new Date());
        Room roomWithMembers = new Room("uuid-bebas", "bebas", "bebas", new Date(), user, new HashSet<>(), new ArrayList<>());

        Set<RoomMember> roomMembers = roomWithMembers.getRoomMember();
        roomMembers.add(roomMember);
        roomWithMembers.setRoomMember(roomMembers);


        Mockito.when(roomService.addMemberToRoom(Mockito.any(String.class), Mockito.any(RoomMember.class))).thenReturn(roomWithMembers);

        // expected
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/rooms/uuid-bebas/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n    \"user\" : {\n        \"id\" : \"testing1\"\n    }\n}");

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("member added to room")))
                .andReturn().getResponse().getContentAsString();

        WebResponse<Room> response = objectMapper.readValue(responseJson, WebResponse.class);
//        response.getData().getRoomMember().size();
//        assertEquals(count,1);

    }
}


