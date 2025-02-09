package org.websocket.chat.service;

import org.websocket.chat.entity.Room;
import java.util.UUID;

public interface RoomService {

    Room createRoom();
    Room getRoom(UUID roomId);

}
