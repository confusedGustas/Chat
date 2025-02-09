package org.websocket.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.websocket.chat.entity.Message;
import org.websocket.chat.entity.Room;
import org.websocket.chat.service.MessageService;
import org.websocket.chat.service.RoomService;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;
    private final MessageService messageService;

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, Message message) {
        Room room = roomService.getRoom(UUID.fromString(roomId));
        if (room != null) {
            message.setRoomId(UUID.fromString(roomId));
            Message saved = messageService.saveMessage(message);
            messagingTemplate.convertAndSend("/topic/rooms/" + roomId, saved);
        }
    }

}