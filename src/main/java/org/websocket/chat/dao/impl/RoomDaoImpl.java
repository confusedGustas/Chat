package org.websocket.chat.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Room;
import org.websocket.chat.integrity.RoomIntegrity;
import org.websocket.chat.repository.RoomRepository;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomDaoImpl implements RoomDao {

    public static final String ROOM_NOT_FOUND = "Room not found";

    private final RoomRepository roomRepository;
    private final RoomIntegrity roomIntegrity;

    @Override
    public Room saveRoom(Room room) {
        roomIntegrity.validateRoom(room);
        return roomRepository.save(room);
    }

    @Override
    public Room findRoomById(UUID id) {
        roomIntegrity.validateRoomId(id);
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ROOM_NOT_FOUND));
    }

}
