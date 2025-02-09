package org.websocket.chat.service;

import org.websocket.chat.entity.Message;

public interface MessageService {

    Message saveMessage(Message message);

}
