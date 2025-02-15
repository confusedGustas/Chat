package org.websocket.chat.service;

import org.websocket.chat.entity.Message;
import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message sendMessage(UUID roomId, Message message);
    List<Message> getMessages(UUID roomId);

}
