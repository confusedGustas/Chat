package org.websocket.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.websocket.chat.entity.Room;
import org.websocket.chat.service.RoomService;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms/create")
    public ResponseEntity<Room> createRoom() {
        return ResponseEntity.ok(roomService.createRoom());
    }

}