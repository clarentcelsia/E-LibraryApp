package com.project.app.service.impl;

import com.project.app.dto.RoomDTO;
import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.repository.RoomRepository;
import com.project.app.service.RoomMemberService;
import com.project.app.service.RoomMessageService;
import com.project.app.service.RoomService;
import com.project.app.specification.RoomSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository repository;

    @Autowired
    private RoomMemberService roomMemberService;

    @Autowired
    private RoomMessageService roomMessageService;

    @Override
    @Transactional
    public Room create(Room room) {
        Room savedRoom = repository.save(room);
        return savedRoom;
    }

    @Override
    public Room getRoomById(String id){
        Optional<Room> room = repository.findById(id);
        if (room.isPresent()){
            return room.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Room with id %s not found", id));
    }

    @Override
    public Page<Room> getPagedRooms(RoomDTO roomDTO, Pageable pageable) {
        Specification<Room> specification = RoomSpecification.getSpecification(roomDTO);
        return repository.findAll(specification, pageable);
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
    public Page<RoomMember> getRoomMembers(String id, Pageable pageable) {
        Set<RoomMember> roomMembers = getRoomById(id).getRoomMember();
        List<RoomMember> memberList = new ArrayList<>(roomMembers);

        Integer total = roomMembers.size();
        Integer start = Math.toIntExact(pageable.getOffset());
        Integer end = Math.min((start + pageable.getPageSize()), total);

        List<RoomMember> output = new ArrayList<>();

        if (start <= end) {
            output = memberList.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageable,
                total
        );
    }

    @Override
    @Transactional
    public Room addMemberToRoom(String roomId, RoomMember roomMember) {
        Room room = getRoomById(roomId);

        roomMember.setRoom(room);
        RoomMember savedMember = roomMemberService.create(roomMember);

        if (savedMember.getUser().equals(room.getUser())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tidak bisa add member pembuat room");
        }

        if (room.getRoomMember().contains(savedMember)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tidak bisa add member yang sama");
        }

        room.getRoomMember().add(savedMember);
        return room;
    }

    @Override
    @Transactional
    public Room addMessageToRoom(String roomId, RoomMessage roomMessage) {
        Room room = getRoomById(roomId);

        roomMessage.setRoom(room);
        roomMessageService.create(roomMessage);
        room.getRoomMessage().add(roomMessage);

        return room;
    }

    @Override
    public Page<RoomMessage> getPagedRoomMessages(String id, Pageable pageable) {
        List<RoomMessage> roomMessage = getRoomById(id).getRoomMessage();

        Integer total = roomMessage.size();
        Integer start = Math.toIntExact(pageable.getOffset());
        Integer end = Math.min((start + pageable.getPageSize()), total);

        List<RoomMessage> output = new ArrayList<>();

        if (start <= end) {
            output = roomMessage.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageable,
                total
        );
    }
}
