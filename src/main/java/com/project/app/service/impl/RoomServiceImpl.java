package com.project.app.service.impl;

import com.project.app.entity.Room;
import com.project.app.repository.RoomRepository;
import com.project.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository repository;

    @Override
    public Room create(Room room) {
        return repository.save(room);
    }

    @Override
    public Room getRoomById(String id) {
        Optional<Room> room = repository.findById(id);
        if (room.isPresent()){
            return room.get();
        }
        throw new RuntimeException(String.format("Room with id %s not Found", id));
    }

    @Override
    public List<Room> getRooms() {
        return repository.findAll();
    }

    @Override
    public String deleteRoomById(String id) {
        repository.delete(getRoomById(id));
        return String.format("Room with id %s Deleted", id);
    }
}
