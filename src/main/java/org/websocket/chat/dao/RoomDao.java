package org.websocket.chat.dao;

import org.websocket.chat.entity.Room;
import java.util.UUID;

public interface RoomDao {

    void save(Room room);
    Room findById(UUID id);

}
