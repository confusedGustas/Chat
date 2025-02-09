package org.websocket.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.entity.Message;
import org.websocket.chat.repository.MessageRepository;
import org.websocket.chat.service.MessageService;
import java.util.Date;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        message.setTimestamp(new Date());
        return messageRepository.save(message);
    }

}
