package org.websocket.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.websocket.chat.entity.Message;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> { }
