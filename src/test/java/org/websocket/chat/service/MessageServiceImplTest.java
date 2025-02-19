package org.websocket.chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.websocket.chat.dao.MessageDao;
import org.websocket.chat.dao.RoomDao;
import org.websocket.chat.entity.Message;
import org.websocket.chat.entity.Room;
import org.websocket.chat.integrity.MessageIntegrity;
import org.websocket.chat.service.impl.MessageServiceImpl;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private MessageDao messageDao;

    @Mock
    private RoomDao roomDao;

    @Mock
    private MessageIntegrity messageIntegrity;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void sendMessage_ValidMessage_SavesAndNotifies() {
        UUID roomId = UUID.randomUUID();
        Room room = new Room();
        Message message = new Message();
        message.setSender("testUser");
        message.setContent("Hello");

        when(roomDao.findRoomById(roomId)).thenReturn(room);
        when(messageDao.getDistinctSendersByRoomId(roomId)).thenReturn(List.of());
        when(messageDao.saveMessage(any())).thenReturn(message);

        messageService.sendMessage(roomId, message);

        verify(messageIntegrity).validateMessage(message);
        verify(messageDao).saveMessage(message);
        verify(messagingTemplate).convertAndSend("/topic/rooms/" + roomId, message);
        assertEquals(room, message.getRoom());
    }

}