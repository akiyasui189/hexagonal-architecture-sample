package io.spring.start.sample.hexagon.adapter.persistences.repositories;

import io.spring.start.sample.hexagon.adapter.persistences.daos.UserDao;
import io.spring.start.sample.hexagon.adapter.persistences.entities.UserEntity;
import io.spring.start.sample.hexagon.adapter.persistences.mappers.UserMapper;
import io.spring.start.sample.hexagon.core.dtos.User;
import io.spring.start.sample.hexagon.core.enums.UserStatus;
import io.spring.start.sample.hexagon.core.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserRepositoryPersistenceImpl implements UserRepository {

    // TODO: use JPA
    //private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public User register (User user) {
        // TODO: use JPA
        UserEntity userEntity = convertToEntity(user);
        userMapper.insert(userEntity);
        return user;
        /*
        UserEntity userEntity = convertToEntity(user);
        userEntity = userDao.save(userEntity);
        return convertToDto(userEntity);
         */
    }

    @Override
    public Optional<User> getUser(Long id, UserStatus status) {
        return Optional.ofNullable(
                userMapper.findByIdAndStatus(id, status.name())
        );
    }

    @Override
    public List<User> getNewRegisteredUsers() {
        return userMapper.findByStatus(UserStatus.REGISTERED.name())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserEntity convertToEntity (User user) {
        return new UserEntity(
                user.getId(),
                user.getAccount(),
                user.getPasswordDigest(),
                user.getStatus().name(),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy(),
                user.getVersion(),
                user.isDeleted()
        );
    }

    private User convertToDto (UserEntity user) {
        return new User(
                user.getId(),
                user.getAccount(),
                user.getPasswordDigest(),
                UserStatus.valueOf(user.getStatus()),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy(),
                user.getVersion(),
                user.isDeleted()
        );
    }
}
