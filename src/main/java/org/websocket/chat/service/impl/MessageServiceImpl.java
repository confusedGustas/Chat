package org.websocket.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.MessageDao;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Message;
import org.websocket.chat.entity.Room;
import org.websocket.chat.service.MessageService;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageDao messageDao;
    private final RoomDao roomDao;

    @Override
    public void sendMessage(UUID roomId, Message message) {
        Room room = roomDao.findById(roomId);

        if (room != null) {
            message.setRoomId(UUID.fromString(String.valueOf(roomId)));
            message.setTimestamp(new Date());

            Message saved = messageDao.saveMessage(message);
            messagingTemplate.convertAndSend("/topic/rooms/" + roomId, saved);
        }
    }

    @Override
    public List<Message> getMessages(UUID roomId) {
        return messageDao.getMessageByRoomId(roomId);
    }

}
