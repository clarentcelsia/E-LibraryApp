package com.project.app.service.impl;

import com.project.app.entity.RoomMember;
import com.project.app.repository.RoomMemberRepository;
import com.project.app.service.RoomMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomMemberServiceImpl implements RoomMemberService {
    @Autowired
    private RoomMemberRepository repository;

    @Override
    public RoomMember create(RoomMember roomMember) {
        return repository.save(roomMember);
    }

    @Override
    public RoomMember getRoomMemberById(String id) {
        Optional<RoomMember> roomMember = repository.findById(id);
        if(roomMember.isPresent()){
            return roomMember.get();
        }
        throw new RuntimeException("Member with %s not found");
    }

    @Override
    public String deleteRoomMemberById(String id) {
        repository.delete(getRoomMemberById(id));
        return String.format("Member with %s deleted",id);
    }
}
