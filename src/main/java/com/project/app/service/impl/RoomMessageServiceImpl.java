package com.project.app.service.impl;

import com.project.app.entity.RoomMessage;
import com.project.app.repository.RoomMessageRepository;
import com.project.app.service.RoomMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomMessageServiceImpl implements RoomMessageService {
    @Autowired
    RoomMessageRepository repository;

    @Override
    public RoomMessage create(RoomMessage roomMessage) {
        return repository.save(roomMessage);
    }
}
