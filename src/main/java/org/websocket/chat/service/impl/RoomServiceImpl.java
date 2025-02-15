package org.websocket.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Room;
import org.websocket.chat.integrity.RoomIntegrity;
import org.websocket.chat.service.RoomService;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomDao roomDao;
    private final RoomIntegrity roomIntegrity;

    @Override
    public Room createRoom() {
        Room room = Room.builder().build();
        roomIntegrity.validateRoom(room);

        return roomDao.saveRoom(room);
    }

}