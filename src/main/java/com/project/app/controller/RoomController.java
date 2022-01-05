package com.project.app.controller;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getRooms(){
        return roomService.getRooms();
    }

    @GetMapping("/{roomId}")
    public Room getRoom(@PathVariable("roomId") String id){
        return roomService.getRoomById(id);
    }

    @GetMapping("/{roomId}/members")
    public Set<RoomMember> getRoomMembers(@PathVariable("roomId") String id){
        return roomService.getRoomMembers(id);
    }

    @GetMapping("/{roomId}/messages")
    public List<RoomMessage> getRoomMessages(@PathVariable("roomId") String id){
        return roomService.getRoomMessages(id);
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room){
        return roomService.create(room);
    }

    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable("roomId") String id){
        return roomService.deleteRoomById(id);
    }

    @PostMapping("/{roomId}/members")
    public Room addMember(
            @PathVariable("roomId") String id,
            @RequestBody RoomMember roomMember
            ){
        return roomService.addMemberToRoom(id,roomMember);
    }

    @DeleteMapping("/{roomId}/members")
    public Room removeMember(
            @PathVariable("roomId") String id,
            @RequestBody RoomMember roomMember
    ){
        return roomService.removeMemberFromRoom(id,roomMember);
    }

    @PostMapping("/{roomId}/messages")
    public Room addMessage(
            @PathVariable("roomId") String id,
            @RequestBody RoomMessage roomMessage
            ){
        return roomService.addMessageToRoom(id,roomMessage);
    }
}
