package com.project.app.repository;

import com.project.app.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, String> {
}