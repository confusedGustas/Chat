package org.websocket.chat.integrity;

import org.websocket.chat.entity.Message;
import java.util.List;

public interface MessageIntegrity {

    void validateMessage(Message message);
    void validateNickname(List<String> messages, String nickname);

}
