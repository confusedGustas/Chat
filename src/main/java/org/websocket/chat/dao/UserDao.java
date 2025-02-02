package org.websocket.chat.dao;

import org.websocket.chat.entity.User;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByEmail(String email);
    User saveUser(User user);

}
