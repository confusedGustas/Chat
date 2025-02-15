package org.websocket.chat.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.MessageDao;
import org.websocket.chat.entity.Message;
import org.websocket.chat.integrity.MessageIntegrity;
import org.websocket.chat.integrity.RoomIntegrity;
import org.websocket.chat.repository.MessageRepository;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageDaoImpl implements MessageDao {

    private final MessageRepository messageRepository;
    private final MessageIntegrity messageIntegrity;
    private final RoomIntegrity roomIntegrity;

    @Override
    public List<Message> getMessageByRoomId(UUID roomId) {
        roomIntegrity.validateRoomId(roomId);
        return messageRepository.findAllByRoomId(roomId);
    }

    @Override
    public Message saveMessage(Message message) {
        messageIntegrity.validateMessage(message);
        return messageRepository.save(message);
    }

    @Override
    public List<String> getDistinctSendersByRoomId(UUID roomId) {
        roomIntegrity.validateRoomId(roomId);
        return messageRepository.findDistinctSendersByRoomId(roomId);
    }

}
