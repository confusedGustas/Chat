package org.websocket.chat.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.websocket.chat.dto.RegisterRequestDto;
import org.websocket.chat.entity.User;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(RegisterRequestDto registerRequestDto) {
        return User.builder()
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .username(registerRequestDto.getUsername())
                .build();
    }

}