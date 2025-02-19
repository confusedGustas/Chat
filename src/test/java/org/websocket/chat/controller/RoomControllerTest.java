package org.websocket.chat.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.websocket.chat.config.SecurityConfig;
import org.websocket.chat.entity.Room;
import org.websocket.chat.service.RoomService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
@ContextConfiguration(classes = {SecurityConfig.class, RoomController.class})
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @Test
    void createRoom_ShouldCallService() throws Exception {
        when(roomService.createRoom()).thenReturn(new Room());

        mockMvc.perform(get("/room/create"))
                .andExpect(status().isOk());

        verify(roomService).createRoom();
    }

}