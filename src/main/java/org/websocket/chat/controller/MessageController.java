package org.websocket.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.websocket.chat.entity.Message;
import org.websocket.chat.service.MessageService;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/message/{roomId}")
    public void sendMessage(@DestinationVariable UUID roomId, Message message) {
        messageService.sendMessage(roomId, message);
    }

    @GetMapping("/messages/{roomId}")
    public List<Message> getMessages(@PathVariable UUID roomId) {
        return messageService.getMessages(roomId);
    }

}