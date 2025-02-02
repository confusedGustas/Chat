package org.websocket.chat.service.jwt;

import org.websocket.chat.dto.LoginRequestDto;
import org.websocket.chat.dto.LoginResponseDto;
import org.websocket.chat.dto.RegisterRequestDto;
import org.websocket.chat.dto.RegisterResponseDto;
import org.websocket.chat.entity.User;

public interface AuthenticationService {

    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    User getAuthenticatedUser();

}