package com.project.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.dto.RoomDTO;
import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.entity.User;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
import com.project.app.service.RoomService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

        Response<Room> response = objectMapper.readValue(responseJson, Response.class);
        assertNull(response.getData());
    }

    @Test
    public void addMember_shouldReturn_StatusCreated_and_RequestedRoomWithAddedMember() throws Exception {

        // preparation room members
        User jannes = new User("testing2", "jannes");
        Room roomWithMembers = new Room("uuid-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());
        RoomMember roomMember = new RoomMember("id-member", null, jannes, new Date());
        roomWithMembers.getRoomMember().add(roomMember);

        String message = "member added to room";
        Mockito.when(roomService.addMemberToRoom(Mockito.anyString(), Mockito.any(RoomMember.class))).thenReturn(roomWithMembers);


        // expected
        Room jsonBody = new Room(null, "mtk", "integral", null, user, null, null);
        String jsonRequest = objectMapper.writeValueAsString(savedRoom);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/rooms/uuid-bebas/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        Response<Room> response = objectMapper.readValue(responseJson, new TypeReference<Response<Room>>() {});
        assertNotNull(response.getData());
        assertEquals(response.getData().getRoomMember().size(), 1);
    }

    @Test
    public void removeMember_shouldReturn_StatusOK_and_RequestedRoomWithDeletedMember() throws Exception {
        // preparation room with no members
        User jannes = new User("testing2", "jannes");
        Room roomWithNoMembers = new Room("uuid-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());

        Mockito.when(roomService.removeMemberFromRoom(Mockito.anyString(), Mockito.any(RoomMember.class))).thenReturn(roomWithNoMembers);

        // expected
        RoomMember jsonBody = new RoomMember("id-member1", null, null, null);
        String jsonRequest = objectMapper.writeValueAsString(jsonBody);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/rooms/uuid-bebas/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        String message = "member removed from room";
        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        Response<Room> response = objectMapper.readValue(responseJson, new TypeReference<Response<Room>>() {});
        assertEquals(response.getData().getRoomMember().size(), 0);
    }

    @Test
    public void addMessage_ShouldReturn_StatusCREATED_and_RequestedRoomWithAddedMessages() throws Exception {
        // preparasi room messages
        User jannes = new User("testing2", "jannes");
        Room roomWithMessages = new Room("uuid-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());
        RoomMessage roomMessage = new RoomMessage("id-message", roomWithMessages, jannes, "Hai guys", new Date());
        roomWithMessages.getRoomMessage().add(roomMessage);

        // actual
        String message = "message was added to room";
        Mockito.when(roomService.addMessageToRoom(Mockito.anyString(), Mockito.any(RoomMessage.class))).thenReturn(roomWithMessages);

        // expected
        Room jsonData = new Room(null, "mtk", "integral", null, user, null, null);
        String jsonRequestBody = objectMapper.writeValueAsString(jsonData);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rooms/uuid-bebas/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody);

        String responseJson = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andReturn().getResponse().getContentAsString();

        Response<Room> response = objectMapper.readValue(responseJson, new TypeReference<Response<Room>>() {});
        assertNotNull(response.getData());
        assertEquals(response.getData().getRoomMessage().size(), 1);
    }

    @Test
    public void getRooms_ShouldReturn_StatusOK_and_AllPagedRooms() throws Exception {
        // preparation all paged rooms
        Sort sort = Sort.by("topic");
        Pageable pageable = PageRequest.of(0,1,sort);
        RoomDTO roomDTO = new RoomDTO("mtk", null);

        Room roomOne = new Room("id-room-1", "mtk", "integral", new Date(), user , new HashSet<>(), new ArrayList<>());
        List<Room> rooms = new ArrayList<>();
        rooms.add(roomOne);
        Page<Room> roomPage = new PageImpl<>(rooms,pageable,rooms.size());

        // actual
        Mockito.when(roomService.getPagedRooms(Mockito.any(RoomDTO.class), Mockito.any(Pageable.class))).thenReturn(roomPage);


        // expected
        String message =  String.format("data halaman ke 1");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/rooms/")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("size", "1")
                .queryParam("page", "0")
                .queryParam("sortBy", "topic")
                .queryParam("direction", "ASC")
                .queryParam("topic", "mtk");


        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)))
                .andExpect(jsonPath("$.['data'].['content'][0].['id']", Matchers.is(roomOne.getId())));

    }

}


