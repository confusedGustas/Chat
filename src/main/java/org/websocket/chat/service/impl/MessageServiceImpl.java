package org.websocket.chat.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.MessageDao;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Message;
import org.websocket.chat.entity.Room;
import org.websocket.chat.service.MessageService;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageDao messageDao;
    private final RoomDao roomDao;

    @Override
    @Transactional
    public Message sendMessage(UUID roomId, Message message) {
        Room room = roomDao.findRoomById(roomId);

        if (room != null) {
            message.setRoom(room);
            room.getMessages().add(message);

            Message saved = messageDao.saveMessage(message);
            messagingTemplate.convertAndSend("/topic/rooms/" + roomId, saved);
        }

        return message;
    }

    @Override
    public List<Message> getMessages(UUID roomId) {
        return messageDao.getMessageByRoomId(roomId);
    }

}