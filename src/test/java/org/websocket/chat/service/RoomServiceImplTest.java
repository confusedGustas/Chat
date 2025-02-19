package org.websocket.chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Room;
import org.websocket.chat.integrity.RoomIntegrity;
import org.websocket.chat.service.impl.RoomServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomDao roomDao;

    @Mock
    private RoomIntegrity roomIntegrity;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void createRoom_ValidRoom_ReturnsRoom() {
        Room room = Room.builder().build();
        when(roomDao.saveRoom(any())).thenReturn(room);

        Room result = roomService.createRoom();

        verify(roomIntegrity).validateRoom(any());
        verify(roomDao).saveRoom(any());
        assertNotNull(result);
    }

}