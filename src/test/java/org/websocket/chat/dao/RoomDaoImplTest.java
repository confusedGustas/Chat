package org.websocket.chat.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.websocket.chat.dao.impl.RoomDaoImpl;
import org.websocket.chat.entity.Room;
import org.websocket.chat.integrity.RoomIntegrity;
import org.websocket.chat.repository.RoomRepository;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomDaoImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomIntegrity roomIntegrity;

    @InjectMocks
    private RoomDaoImpl roomDao;

    @Test
    void saveRoom_ValidRoom_ReturnsSavedRoom() {
        Room room = new Room();
        when(roomRepository.save(room)).thenReturn(room);

        Room result = roomDao.saveRoom(room);

        verify(roomIntegrity).validateRoom(room);
        assertEquals(room, result);
    }

    @Test
    void findRoomById_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomDao.findRoomById(id));
        verify(roomIntegrity).validateRoomId(id);
    }

}