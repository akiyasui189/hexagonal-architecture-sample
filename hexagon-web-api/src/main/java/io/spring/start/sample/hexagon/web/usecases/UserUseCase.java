package io.spring.start.sample.hexagon.web.usecases;

import io.spring.start.sample.hexagon.core.dtos.User;
import io.spring.start.sample.hexagon.core.enums.UserStatus;
import io.spring.start.sample.hexagon.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserUseCase {

    private final UserService userService;

    @Transactional
    public User registerUser (String account, String password, String confirmPassword) {
        return userService.register(account, password, confirmPassword);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUser (Long id, UserStatus status) {
        return userService.getUser(id, status);
    }

    @Transactional(readOnly = true)
    public List<User> getNewRegisteredUsers () {
        return userService.getNewRegisteredUsers();
    }

}
