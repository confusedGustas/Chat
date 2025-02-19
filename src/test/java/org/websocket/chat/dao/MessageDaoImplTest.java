package org.websocket.chat.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.websocket.chat.dao.impl.MessageDaoImpl;
import org.websocket.chat.entity.Message;
import org.websocket.chat.integrity.MessageIntegrity;
import org.websocket.chat.integrity.RoomIntegrity;
import org.websocket.chat.repository.MessageRepository;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageDaoImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageIntegrity messageIntegrity;

    @Mock
    private RoomIntegrity roomIntegrity;

    @InjectMocks
    private MessageDaoImpl messageDao;

    @Test
    void getMessageByRoomId_ValidRoomId_ReturnsMessages() {
        UUID roomId = UUID.randomUUID();
        List<Message> expected = List.of(new Message());
        when(messageRepository.findAllByRoomId(roomId)).thenReturn(expected);

        List<Message> result = messageDao.getMessageByRoomId(roomId);

        verify(roomIntegrity).validateRoomId(roomId);
        assertEquals(expected, result);
    }

    @Test
    void saveMessage_ValidMessage_ReturnsSavedMessage() {
        Message message = new Message();
        when(messageRepository.save(message)).thenReturn(message);

        Message result = messageDao.saveMessage(message);

        verify(messageIntegrity).validateMessage(message);
        assertEquals(message, result);
    }

}