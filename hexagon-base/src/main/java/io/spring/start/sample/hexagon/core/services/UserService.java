package io.spring.start.sample.hexagon.core.services;

import io.spring.start.sample.hexagon.core.dtos.User;
import io.spring.start.sample.hexagon.core.enums.UserStatus;
import io.spring.start.sample.hexagon.core.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User register(String account, String password, String confirmPassword) {
        // confirm password
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Can not confirm password.");
        }
        // create password digest
        String passwordDigest = DigestUtils.sha256Hex(password);
        // registered
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                account,
                passwordDigest,
                UserStatus.REGISTERED,
                now,
                account,
                now,
                account,
                0,
                false
        );
        return userRepository.register(user);
    }

    public Optional<User> getUser (Long id, UserStatus status) {
        return userRepository.getUser(id, status);
    }

    public List<User> getNewRegisteredUsers () {
        return userRepository.getNewRegisteredUsers();
    }
}