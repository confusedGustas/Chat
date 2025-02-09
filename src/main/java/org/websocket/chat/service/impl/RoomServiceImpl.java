package org.websocket.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.entity.Room;
import org.websocket.chat.repository.RoomRepository;
import org.websocket.chat.service.RoomService;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room createRoom() {
        return roomRepository.save(new Room());
    }

    @Override
    public Room getRoom(UUID roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        return room.orElse(null);
    }

}
