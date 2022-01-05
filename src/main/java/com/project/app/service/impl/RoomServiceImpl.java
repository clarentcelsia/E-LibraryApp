package com.project.app.service.impl;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.repository.RoomRepository;
import com.project.app.service.RoomMemberService;
import com.project.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository repository;

    @Autowired
    private RoomMemberService roomMemberService;

    @Override
    @Transactional
    public Room create(Room room) {
        Room savedRoom = repository.save(room);
        return savedRoom;
    }

    @Override
    public Room getRoomById(String id) {
        Optional<Room> room = repository.findById(id);
        if (room.isPresent()){
            return room.get();
        }
        throw new RuntimeException(String.format("Room with id %s not Found", id));
    }

    @Override
    public List<Room> getRooms() {
        return repository.findAll();
    }

    @Override
    public String deleteRoomById(String id) {
        repository.delete(getRoomById(id));
        return String.format("Room with id %s Deleted", id);
    }

    @Override
    @Transactional
    public Room removeMemberFromRoom(String roomId, RoomMember roomMember) {
        Room room = getRoomById(roomId);
        RoomMember member = roomMemberService.getRoomMemberById(roomMember.getId());
        roomMemberService.deleteRoomMemberById(roomMember.getId());

        room.getRoomMember().remove(member);
        return room;
    }

    @Override
    public Set<RoomMember> getRoomMembers(String id) {
        Room room = getRoomById(id);
        return room.getRoomMember();
    }

    @Override
    @Transactional
    public Room addMemberToRoom(String roomId, RoomMember roomMember) {
        Room room = getRoomById(roomId);

        roomMember.setRoom(room);
        RoomMember savedMember = roomMemberService.create(roomMember);


//        System.out.println("testing areea================");
        // USER KUPAKE EQUAL DENGAN ID KARENA SAVEDMEMBER BELUM PERSIST ( NAMA USER MASIH NULL )
//        System.out.println(savedMember.getUser().equals(room.getUser()));
//        System.out.println(savedMember);
//        System.out.println(room.getRoomMember());
//
//        System.out.println(room.getRoomMember().contains(savedMember));

        // tambahin validasi untuk member yang sama
        if (savedMember.getUser().equals(room.getUser())){
            throw new RuntimeException("Tidak bisa add member pembuat room");
        }

        if (room.getRoomMember().contains(savedMember)){
            throw new RuntimeException("Tidak bisa add member yang sama");
        }

        room.getRoomMember().add(savedMember);
        return room;
    }
}
