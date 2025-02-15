package org.websocket.chat.dao;

import org.websocket.chat.entity.Room;
import java.util.UUID;

public interface RoomDao {

    Room saveRoom(Room room);
    Room findRoomById(UUID id);

}
