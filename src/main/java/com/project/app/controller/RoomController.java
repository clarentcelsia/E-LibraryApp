package com.project.app.controller;

import com.project.app.dto.RoomDTO;
import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.response.PageResponse;
import com.project.app.response.WebResponse;
import com.project.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<PageResponse<Room>> getRooms(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "topic") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "topic", required = false) String topic,
            @RequestParam(name = "subTopic", required = false) String subTopic
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        RoomDTO roomDTO = new RoomDTO(topic,subTopic);
        Page<Room> pagedRooms = roomService.getPagedRooms(roomDTO, pageable);

        HttpStatus httpStatus =HttpStatus.OK;
        String message = String.format("data halaman ke %d" ,page+1);
        PageResponse<Room> response = new PageResponse<>(
                pagedRooms.getContent(),
                message,
                pagedRooms.getTotalElements(),
                pagedRooms.getTotalPages(), page+1, size );

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<WebResponse<Room>> getRoom(@PathVariable("roomId") String id){
        Room room = roomService.getRoomById(id);
        String message = String.format("room with id %s found", id);
        WebResponse<Room> response = new WebResponse<>(message, room);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WebResponse<Room>> createRoom(@RequestBody Room room){
        Room savedRoom = roomService.create(room);
        WebResponse<Room> response = new WebResponse<>("room created",savedRoom );

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<WebResponse<String>> deleteRoom(@PathVariable("roomId") String id){
        String message = roomService.deleteRoomById(id);
        WebResponse<String> response = new WebResponse<>(message,null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/{roomId}/members")
    public ResponseEntity<WebResponse<Room>> addMember(
            @PathVariable("roomId") String id,
            @RequestBody RoomMember roomMember
            ){
        Room savedRoom = roomService.addMemberToRoom(id,roomMember);
        WebResponse<Room> response = new WebResponse<>("member added to room",savedRoom );
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomId}/members")
    public ResponseEntity<WebResponse<Room>> removeMember(
            @PathVariable("roomId") String id,
            @RequestBody RoomMember roomMember
    ){
        Room savedRoom = roomService.removeMemberFromRoom(id,roomMember);
        WebResponse<Room> response = new WebResponse<>("member removed from room",savedRoom );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/{roomId}/messages")
    public ResponseEntity<WebResponse<Room>> addMessage(
            @PathVariable("roomId") String id,
            @RequestBody RoomMessage roomMessage
            ){
        Room savedRoom = roomService.addMessageToRoom(id,roomMessage);
        WebResponse<Room> response = new WebResponse<>("message was added to room",savedRoom );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
