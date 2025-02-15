package org.websocket.chat.dao;

import org.websocket.chat.entity.Message;
import java.util.List;
import java.util.UUID;

public interface MessageDao {

    List<Message> getMessageByRoomId(UUID roomId);
    Message saveMessage(Message message);
    List<String> getDistinctSendersByRoomId(UUID roomId);

}
