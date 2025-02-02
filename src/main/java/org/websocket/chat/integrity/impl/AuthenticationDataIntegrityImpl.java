package org.websocket.chat.integrity.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.websocket.chat.dao.UserDao;
import org.websocket.chat.dto.RegisterRequestDto;
import org.websocket.chat.entity.User;
import org.websocket.chat.integrity.AuthenticationDataIntegrity;

@Service
@AllArgsConstructor
public class AuthenticationDataIntegrityImpl implements AuthenticationDataIntegrity {

    public static final String EMAIL_IS_ALREADY_IN_USE = "Email is already in use";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String AUTHENTICATED_PRINCIPAL_IS_NOT_A_USER = "Authenticated principal is not a User";

    private final UserDao userDao;

    @Override
    public void checkEmailNotInUse(RegisterRequestDto registerRequestDto) {
        var existingUser = userDao.getUserByEmail(registerRequestDto.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException(EMAIL_IS_ALREADY_IN_USE);
        }
    }

    @Override
    public User validateUserExists(String email) {
        return userDao.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
    }

    @Override
    public void validateAuthenticatedUser(Object principal) throws IllegalStateException {
        if (!(principal instanceof User)) {
            throw new IllegalStateException(AUTHENTICATED_PRINCIPAL_IS_NOT_A_USER);
        }
    }

}
