package org.websocket.chat.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.MessageDao;
import org.websocket.chat.entity.Message;
import org.websocket.chat.repository.MessageRepository;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageDaoImpl implements MessageDao {

    private final MessageRepository messageRepository;

    @Override
    public List<Message> getMessageByRoomId(UUID roomId) {
        return messageRepository.findAllByRoomId(roomId);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

}
