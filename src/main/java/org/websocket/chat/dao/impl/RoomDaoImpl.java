package org.websocket.chat.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Room;
import org.websocket.chat.repository.RoomRepository;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomDaoImpl implements RoomDao {

    private final RoomRepository roomRepository;

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room findById(UUID id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

}
