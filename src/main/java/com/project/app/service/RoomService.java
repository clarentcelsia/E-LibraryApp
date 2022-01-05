package com.project.app.service;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;

import java.util.List;
import java.util.Set;

public interface RoomService {
    public Room create(Room room);
    public Room getRoomById(String id);
    public List<Room> getRooms();
    public String deleteRoomById(String id);

    public Room addMemberToRoom(String roomId, RoomMember roomMember);
    public Room removeMemberFromRoom(String roomId, RoomMember roomMember);
    public Set<RoomMember> getRoomMembers(String id);
}
