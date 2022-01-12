package com.project.app.service;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;

import java.util.List;

public interface RoomMemberService {
    public RoomMember create(RoomMember roomMember);
    public RoomMember getRoomMemberById(String id);
    public String deleteRoomMemberById(String id);
}
