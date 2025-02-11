package org.websocket.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.websocket.chat.entity.Room;
import org.websocket.chat.service.RoomService;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room/create")
    public Room createRoom() {
        return roomService.createRoom();
    }

}