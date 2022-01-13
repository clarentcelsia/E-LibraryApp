package com.project.app.service;

import com.project.app.entity.Slot;
import com.project.app.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SlotService {

    @Autowired
    SlotRepository slotRepository;

    public Slot saveSlot(Slot slot){
        return slotRepository.save(slot);
    }

    public Page<Slot> getAll(Pageable pageable){
        return slotRepository.findAll(pageable);
    }

    public Slot updateSlot(Slot slot){
        return saveSlot(slot);
    }

    public void deleteSlotByClientId(Integer slot, String clientId){
        slotRepository.findSlot(slot,clientId);
    }
}
