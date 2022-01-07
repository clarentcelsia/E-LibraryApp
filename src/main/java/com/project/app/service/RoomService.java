package com.project.app.service;

import com.project.app.dto.RoomDTO;
import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    public Room create(Room room);
    public Room getRoomById(String id);
    public Page<Room> getPagedRooms(RoomDTO roomDTO, Pageable pageable);
    public String deleteRoomById(String id);

    // member - room transaction
    public Room addMemberToRoom(String roomId, RoomMember roomMember);
    public Room removeMemberFromRoom(String roomId, RoomMember roomMember);
    public Page<RoomMember> getRoomMembers(String id, Pageable pageable);

    // message - room transaction
    public Room addMessageToRoom(String roomId, RoomMessage roomMessage);
    public Page<RoomMessage> getPagedRoomMessages(String id, Pageable pageable);
}
