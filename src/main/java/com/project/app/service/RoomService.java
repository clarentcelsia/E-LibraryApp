package com.project.app.service;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;

import java.util.List;
import java.util.Set;

public interface RoomService {
    public Room create(Room room);
    public Room getRoomById(String id);
    public List<Room> getRooms();
    public String deleteRoomById(String id);

    // member - room transaction
    public Room addMemberToRoom(String roomId, RoomMember roomMember);
    public Room removeMemberFromRoom(String roomId, RoomMember roomMember);
    public Set<RoomMember> getRoomMembers(String id);

    // message - room transaction
    public Room addMessageToRoom(String roomId, RoomMessage roomMessage);
    public List<RoomMessage> getRoomMessages(String id);
}
