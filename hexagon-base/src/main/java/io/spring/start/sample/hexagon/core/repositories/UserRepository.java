package io.spring.start.sample.hexagon.core.repositories;

import io.spring.start.sample.hexagon.core.dtos.User;
import io.spring.start.sample.hexagon.core.enums.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User register (User user);
    Optional<User> getUser (Long id, UserStatus userStatus);
    List<User> getNewRegisteredUsers ();
}
