package com.project.app.controller;

import com.project.app.dto.RoomDTO;
import com.project.app.entity.Room;
import com.project.app.entity.RoomMember;
import com.project.app.entity.RoomMessage;
import com.project.app.response.PageResponse;
import com.project.app.response.Response;
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
    public ResponseEntity<Response<PageResponse<Room>>> getRooms(
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

        String message = String.format("data halaman ke %d", page+1);
        HttpStatus httpStatus =HttpStatus.OK;
        PageResponse<Room> response = new PageResponse<>(
                pagedRooms.getContent(),
                pagedRooms.getTotalElements(),
                pagedRooms.getTotalPages(), page+1, size );

        return ResponseEntity.ok(
                new Response<>(message, response));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Response<Room>> getRoom(@PathVariable("roomId") String id){
        Room room = roomService.getRoomById(id);
        String message = String.format("room with id %s found", id);
        Response<Room> response = new Response<>(message, room);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<Room>> createRoom(@RequestBody Room room){
        Room savedRoom = roomService.create(room);
        Response<Room> response = new Response<>("room created",savedRoom );

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Response<String>> deleteRoom(@PathVariable("roomId") String id){
        String message = roomService.deleteRoomById(id);
        Response<String> response = new Response<>(message,null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/{roomId}/members")
    public ResponseEntity<Response<Room>> addMember(
            @PathVariable("roomId") String id,
            @RequestBody RoomMember roomMember
            ){
        Room savedRoom = roomService.addMemberToRoom(id,roomMember);
        Response<Room> response = new Response<>("member added to room",savedRoom );
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomId}/members")
    public ResponseEntity<Response<Room>> removeMember(
            @PathVariable("roomId") String id,
            @RequestBody RoomMember roomMember
    ){
        Room savedRoom = roomService.removeMemberFromRoom(id,roomMember);
        Response<Room> response = new Response<>("member removed from room",savedRoom );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/{roomId}/messages")
    public ResponseEntity<Response<Room>> addMessage(
            @PathVariable("roomId") String id,
            @RequestBody RoomMessage roomMessage
            ){
        Room savedRoom = roomService.addMessageToRoom(id,roomMessage);
        Response<Room> response = new Response<>("message was added to room",savedRoom );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
