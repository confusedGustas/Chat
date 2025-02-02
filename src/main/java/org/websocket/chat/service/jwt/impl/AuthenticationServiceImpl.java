package org.websocket.chat.service.jwt.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.UserDao;
import org.websocket.chat.dto.LoginRequestDto;
import org.websocket.chat.dto.LoginResponseDto;
import org.websocket.chat.dto.RegisterRequestDto;
import org.websocket.chat.dto.RegisterResponseDto;
import org.websocket.chat.entity.User;
import org.websocket.chat.integrity.AuthenticationDataIntegrity;
import org.websocket.chat.mapper.RegisterMapper;
import org.websocket.chat.mapper.UserMapper;
import org.websocket.chat.service.jwt.AuthenticationService;
import org.websocket.chat.service.jwt.JwtService;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String USER_IS_NOT_AUTHENTICATED = "User is not authenticated.";
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationDataIntegrity authenticationDataIntegrity;
    private final RegisterMapper registerMapper;

    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        authenticationDataIntegrity.checkEmailNotInUse(registerRequestDto);

        return registerMapper.toDto(userDao.saveUser(userMapper.toUser(registerRequestDto)));
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        var user = authenticationDataIntegrity.validateUserExists(loginRequestDto.getEmail());

        return LoginResponseDto.builder().token(jwtService.generateToken(user)).build();
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException(USER_IS_NOT_AUTHENTICATED);
        }

        Object principal = authentication.getPrincipal();
        authenticationDataIntegrity.validateAuthenticatedUser(principal);

        return (User) principal;
    }

}