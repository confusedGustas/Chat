package org.websocket.chat.integrity.impl;

import org.springframework.stereotype.Service;
import org.websocket.chat.entity.Message;
import org.websocket.chat.exception.InvalidNicknameException;
import org.websocket.chat.exception.MessageValidationException;
import org.websocket.chat.integrity.MessageIntegrity;
import java.util.List;

@Service
public class MessageIntegrityImpl implements MessageIntegrity {

    public static final String MESSAGE_CONTENT_CANNOT_BE_EMPTY = "Message content cannot be empty";
    public static final String SENDER_CANNOT_BE_EMPTY = "Sender cannot be empty";
    public static final String NICKNAME_ALREADY_IN_USE = "Nickname already in use";

    @Override
    public void validateMessage(Message message) {
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new MessageValidationException(MESSAGE_CONTENT_CANNOT_BE_EMPTY);
        }
        if (message.getSender() == null || message.getSender().trim().isEmpty()) {
            throw new MessageValidationException(SENDER_CANNOT_BE_EMPTY);
        }
    }

    @Override
    public void validateNickname(List<String> messages, String nickname) {
        if (messages.contains(nickname)) {
            throw new InvalidNicknameException(NICKNAME_ALREADY_IN_USE);
        }
    }

}