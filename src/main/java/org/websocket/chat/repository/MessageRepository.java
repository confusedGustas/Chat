package org.websocket.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.websocket.chat.entity.Message;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("SELECT DISTINCT m.sender FROM Message m WHERE m.room.id = :roomId")
    List<String> findDistinctSendersByRoomId(UUID roomId);

    List<Message> findAllByRoomId(UUID roomId);

}
