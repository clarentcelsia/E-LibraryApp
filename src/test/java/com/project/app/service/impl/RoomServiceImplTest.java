package com.project.app.service.impl;

import com.project.app.dto.RoomDTO;
import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.entity.User;
import com.project.app.repository.RoomRepository;
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
class RoomServiceImplTest {
    @InjectMocks
    RoomServiceImpl service;

    @Mock
    RoomRepository repository;

    @Mock
    RoomMemberServiceImpl roomMemberService;

    @Mock
    RoomMessageServiceImpl roomMessageService;

    private Room room;
    private User user;

    @BeforeEach
    public void setup(){
        user = new User("testing1", "jannes");

        room = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());
    }

    @Test
    public void create_Should_AddOneRoomtoCollection(){
        Room inputRoom = new Room(null, "mtk", "integral", null, user, null, null);

        Mockito.when(repository.save(inputRoom)).thenReturn(room);

        // create room and added to db
        Room savedRoom = service.create(inputRoom);
        List<Room> rooms = new ArrayList<>();
        rooms.add(savedRoom);

        // actual
        Mockito.when(repository.findAll()).thenReturn(rooms);
        assertEquals(repository.findAll().size(), 1);
    }

    @Test
    public void getRoomById_ShouldReturn_RequestedRoom_When_IdExist(){
        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(room));

        Room roomById = service.getRoomById("id-bebas");
        assertEquals(roomById.getId(), "id-bebas");
    }

    @Test
    public void getRoomById_ShouldThrows_ExceptionWithMessageAndNotFoundCode_when_IdNotExist(){
        Mockito.when(repository.findById("id-asal")).thenReturn(Optional.empty());

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.getRoomById("id-asal"));
        assertEquals(e.getReason(), "Room with id id-asal not found");
        assertEquals(e.getRawStatusCode(), 404);
    }

    @Test
    public void getPagedRooms_ShouldReturn_AllPagedRooms(){
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("id-bebas1", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>()));

        RoomDTO dto = new RoomDTO("mtk", null);
        Sort sort = Sort.by(Sort.Direction.ASC, "topic");
        Pageable pageable = PageRequest.of(0,1, sort);

        Page<Room> roomPage = new PageImpl<>(rooms, pageable, rooms.size());

        // actual
        Mockito.when(repository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(roomPage);

        // expected
        Page<Room> pagedRooms = service.getPagedRooms(dto, pageable);
        assertEquals(pagedRooms.getTotalElements(), 1);
    }

    @Test
    public void deleteRoomById_ShouldReturn_message(){
        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(room));

        String message = service.deleteRoomById("id-bebas");
        assertEquals(message,"room with id id-bebas deleted");
    }

    // ===================== Room - Members Test Transaction =================
    @Test
    public void addMembersToRoom_Should_AddMembersToRoom(){
        User jono = new User("testing2", "jono");
        RoomMember roomMember = new RoomMember("id-member1", null, jono, new Date());
        Room roomWithMembers = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());

        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(roomWithMembers));
        Mockito.when(roomMemberService.create(roomMember)).thenReturn(roomMember);

        Room expectedRoom = service.addMemberToRoom("id-bebas", roomMember);

        assertEquals(expectedRoom.getRoomMember().size(), 1);
        assertTrue(expectedRoom.getRoomMember().contains(roomMember));
    }

    @Test
    public void addMembersToRoom_ShouldNot_AddMembers_When_MemberAreRoomCreator(){
        User jannes = new User("testing1", "jannes");
        RoomMember roomMember = new RoomMember("id-member1", null, jannes, new Date());
        Room roomWithMembers = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());

        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(roomWithMembers));
        Mockito.when(roomMemberService.create(roomMember)).thenReturn(roomMember);

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.addMemberToRoom("id-bebas", roomMember));

        assertEquals(e.getRawStatusCode(), 400);
        assertEquals(e.getReason(), "Tidak bisa add member pembuat room");
    }

    @Test
    public void addMemberstoRoom_shoudNot_AddExistingMembers(){
        User jono = new User("testing2", "jono");
        RoomMember roomMember = new RoomMember("id-member1", null, jono, new Date());
        Room roomWithMembers = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());

        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(roomWithMembers));
        Mockito.when(roomMemberService.create(roomMember)).thenReturn(roomMember);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> {
                    service.addMemberToRoom("id-bebas", roomMember);
                    service.addMemberToRoom("id-bebas", roomMember);
                }
        );

        assertEquals(e.getRawStatusCode(), 400);
        assertEquals(e.getReason(), "Tidak bisa add member yang sama");
    }

    @Test
    public void removeMemberstoRoom_Shoud_remove_MembersFromRoom(){
        User jono = new User("testing2", "jono");
        Room roomWithMembers = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());
        RoomMember roomMember = new RoomMember("id-member1", roomWithMembers, jono, new Date());
        roomWithMembers.getRoomMember().add(roomMember);

        Mockito.when(roomMemberService.getRoomMemberById("id-member1")).thenReturn(roomMember);
        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(roomWithMembers));

        Room expectedRoom = service.removeMemberFromRoom("id-bebas", roomMember);

        assertEquals(expectedRoom.getRoomMember().size(), 0);
        assertFalse(expectedRoom.getRoomMember().contains(roomMember));
    }

    @Test
    public void getRoomMembers_ShoudReturn_PagedMembers(){
        // preparasi, add 2 members to room
        User jono = new User("testing2", "jono");
        User rexus = new User("testing3", "rexus");

        Room roomWithMembers = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());

        RoomMember roomMember = new RoomMember("id-member1", roomWithMembers, jono, new Date());
        RoomMember roomMember2 = new RoomMember("id-member2", roomWithMembers, rexus, new Date());

        roomWithMembers.getRoomMember().add(roomMember);
        roomWithMembers.getRoomMember().add(roomMember2);

        // actual
        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(roomWithMembers));

        Page<RoomMember> expectedMembers = service.getRoomMembers("id-bebas", PageRequest.of(0, 2));

        assertEquals(expectedMembers.getTotalElements(), 2);
        // checking if members is the same as returned page
        assertTrue(expectedMembers.getContent().containsAll(roomWithMembers.getRoomMember()));
    }

    // ===================== Room - Message Test Transaction =================
    @Test
    public void addMessageToRoom_Should_AddTwoMessageToRooms_WhenAddTwoMessagesToRooms(){
        User jono = new User("testing2", "jono");
        Room roomWithMessages = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());
        RoomMessage roomMessage = new RoomMessage("id-pesan", roomWithMessages, user, "halo guys", new Date());
        RoomMessage roomMessage2 = new RoomMessage("id-pesan2", roomWithMessages, user, "halo guys", new Date());


        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(roomWithMessages));
        Mockito.when(roomMessageService.create(roomMessage)).thenReturn(roomMessage);

        service.addMessageToRoom("id-bebas", roomMessage);
        Room expectedRoom = service.addMessageToRoom("id-bebas", roomMessage2);

        assertEquals(expectedRoom.getRoomMessage().size(), 2);
        assertTrue(expectedRoom.getRoomMessage().contains(roomMessage));
    }

    @Test
    public void getPagedRoomMessages_ShoudReturn_PagedMessages(){
        // preparasi, add 2 messages to room
        User jono = new User("testing2", "jono");
        Room roomWithMessages = new Room("id-bebas", "mtk", "integral", new Date(), user, new HashSet<>(), new ArrayList<>());
        RoomMessage roomMessage = new RoomMessage("id-pesan", roomWithMessages, jono, "halo guys", new Date());
        RoomMessage roomMessage2 = new RoomMessage("id-pesan2", roomWithMessages, jono, "halo guys", new Date());

        roomWithMessages.getRoomMessage().add(roomMessage);
        roomWithMessages.getRoomMessage().add(roomMessage2);

        // actual
        Mockito.when(repository.findById("id-bebas")).thenReturn(Optional.of(roomWithMessages));

        Page<RoomMessage> expectedMessages = service.getPagedRoomMessages("id-bebas", PageRequest.of(1, 2));

        assertEquals(expectedMessages.getTotalElements(), 2);
        // check if messages is the same as returned paged messages
    }
}