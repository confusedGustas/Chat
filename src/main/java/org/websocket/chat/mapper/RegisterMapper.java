package org.websocket.chat.mapper;

import org.springframework.stereotype.Component;
import org.websocket.chat.dto.RegisterResponseDto;
import org.websocket.chat.entity.User;

@Component
public class RegisterMapper {

    public RegisterResponseDto toDto(User user) {
        return RegisterResponseDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

}