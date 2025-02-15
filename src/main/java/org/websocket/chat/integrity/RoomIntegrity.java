package org.websocket.chat.integrity;

import org.websocket.chat.entity.Room;
import java.util.UUID;

public interface RoomIntegrity {

    void validateRoom(Room room);
    void validateRoomId(UUID id);

}
