package com.project.app.service.impl;

import com.project.app.entity.RoomMember;
import com.project.app.repository.RoomMemberRepository;
import com.project.app.service.RoomMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomMemberServiceImpl implements RoomMemberService {
    @Autowired
    private RoomMemberRepository repository;

    @Override
    public RoomMember create(RoomMember roomMember) {
        return repository.save(roomMember);
    }
}
