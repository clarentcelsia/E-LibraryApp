package com.project.app.controller;

import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public Room createRoom(@RequestBody Room room){
        return roomService.create(room);
    }

    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable("roomId") String id){
        return roomService.deleteRoomById(id);
    }

    @PostMapping("/{roomId}")
    public Room createRoom(
            @PathVariable("roomId") String id,
            @RequestBody RoomMember roomMember
            ){
        return roomService.addMemberToRoom(id,roomMember);
    }
}
