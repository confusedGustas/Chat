package org.websocket.chat.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.entity.Room;
import org.websocket.chat.repository.RoomRepository;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomDaoImpl implements RoomDao {

    private final RoomRepository roomRepository;

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findById(UUID id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

}
