package org.websocket.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @MessageMapping("/message/send/{roomId}")
    public void sendMessage(@DestinationVariable UUID roomId, Message message) {
        messageService.sendMessage(roomId, message);
    }

    @GetMapping("/message/get/all/{roomId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable UUID roomId) {
        return ResponseEntity.ok(messageService.getMessages(roomId));
    }

}