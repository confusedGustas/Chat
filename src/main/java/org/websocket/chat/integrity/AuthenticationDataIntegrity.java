package org.websocket.chat.integrity;

import org.websocket.chat.dto.RegisterRequestDto;
import org.websocket.chat.entity.User;

public interface AuthenticationDataIntegrity {

    void checkEmailNotInUse(RegisterRequestDto registerRequestDto);
    User validateUserExists(String email);
    void validateAuthenticatedUser(Object principal);

}