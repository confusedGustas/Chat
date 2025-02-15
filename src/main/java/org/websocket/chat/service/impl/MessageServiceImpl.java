package org.websocket.chat.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.MessageDao;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Message;
import org.websocket.chat.entity.Room;
import org.websocket.chat.integrity.MessageIntegrity;
import org.websocket.chat.service.MessageService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    public static final String TOPIC_ROOMS = "/topic/rooms/";

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageDao messageDao;
    private final RoomDao roomDao;
    private final MessageIntegrity messageIntegrity;

    @Override
    @Transactional
    public Message sendMessage(UUID roomId, Message message) {
        Room room = roomDao.findRoomById(roomId);
        messageIntegrity.validateMessage(message);
        messageIntegrity.validateNickname(messageDao.getDistinctSendersByRoomId(roomId), message.getSender());

        assignRoomToMessage(room, message);
        Message savedMessage = persistMessage(message);
        notifySubscribers(roomId, savedMessage);

        return savedMessage;
    }

    @Override
    public List<Message> getMessages(UUID roomId) {
        return messageDao.getMessageByRoomId(roomId);
    }

    private Message persistMessage(Message message) {
        return messageDao.saveMessage(message);
    }

    private void notifySubscribers(UUID roomId, Message message) {
        messagingTemplate.convertAndSend(TOPIC_ROOMS + roomId, message);
    }

    private void assignRoomToMessage(Room room, Message message) {
        message.setRoom(room);
        room.getMessages().add(message);
    }

}