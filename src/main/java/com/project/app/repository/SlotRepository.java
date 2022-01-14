package com.project.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, String> {

    @Modifying
    @Query(value = "UPDATE Slot s SET s.slot = ?1 WHERE s.clients = ?2", nativeQuery = true)
    void findSlot(Integer slot, String clientId);
}
