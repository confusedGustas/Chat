package org.websocket.chat.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.websocket.chat.config.SecurityConfig;
import org.websocket.chat.entity.Message;
import org.websocket.chat.service.MessageService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@ContextConfiguration(classes = {SecurityConfig.class, MessageController.class})
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    @Test
    void sendMessage_ShouldCallService() {
        UUID roomId = UUID.randomUUID();
        Message message = new Message();
        MessageController controller = new MessageController(messageService);
        controller.sendMessage(roomId, message);
        verify(messageService).sendMessage(roomId, message);
    }

    @Test
    void getMessages_ShouldReturnMessages() throws Exception {
        UUID roomId = UUID.randomUUID();
        when(messageService.getMessages(roomId)).thenReturn(List.of());

        mockMvc.perform(get("/message/get/all/{roomId}", roomId))
                .andExpect(status().isOk());

        verify(messageService).getMessages(roomId);
    }

}