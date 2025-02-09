package org.websocket.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.websocket.chat.entity.Room;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> { }
