package org.websocket.chat.integrity.impl;

import org.springframework.stereotype.Service;
import org.websocket.chat.entity.Room;
import org.websocket.chat.exception.exceptions.InvalidRoomIdException;
import org.websocket.chat.exception.exceptions.RoomNotFoundException;
import org.websocket.chat.integrity.RoomIntegrity;
import java.util.UUID;

@Service
public class RoomIntegrityImpl implements RoomIntegrity {

    public static final String ROOM_NAME_CANNOT_BE_EMPTY = "Room name cannot be empty";

    @Override
    public void validateRoom(Room room) {
        if (room == null) {
            throw new RoomNotFoundException(ROOM_NAME_CANNOT_BE_EMPTY);
        }
    }

    @Override
    public void validateRoomId(UUID id) {
        if (id == null) {
            throw new InvalidRoomIdException(ROOM_NAME_CANNOT_BE_EMPTY);
        }
    }

}
