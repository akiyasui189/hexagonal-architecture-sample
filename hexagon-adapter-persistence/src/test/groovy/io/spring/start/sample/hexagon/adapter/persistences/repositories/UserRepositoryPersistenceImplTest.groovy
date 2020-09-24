package io.spring.start.sample.hexagon.adapter.persistences.repositories

import io.spring.start.sample.hexagon.adapter.persistences.entities.UserEntity
import io.spring.start.sample.hexagon.adapter.persistences.mappers.UserMapper
import io.spring.start.sample.hexagon.core.dtos.User
import io.spring.start.sample.hexagon.core.enums.UserStatus
import spock.lang.Specification

import java.time.LocalDateTime

class UserRepositoryPersistenceImplTest extends Specification {

    // TODO: add test code

    def "test convertToEntity()" () {
        given:
        def userMapper = Mock(UserMapper)
        def target = new UserRepositoryPersistenceImpl(userMapper)
        def now = LocalDateTime.now()
        def user = new User(
                "account",
                "passwordDigest",
                UserStatus.REGISTERED,
                now,
                "createdBy",
                now,
                "updatedBy",
                0,
                false
        )
        when:
        def actual = target.convertToEntity(user)
        then:
        assert Optional.ofNullable(actual).isPresent()
        assert actual.getId() == null
        assert actual.getAccount() == "account"
        assert actual.getPasswordDigest() == "passwordDigest"
        assert actual.getStatus() == UserStatus.REGISTERED.name()
        assert actual.getCreatedAt() == now
        assert actual.getCreatedBy() == "createdBy"
        assert actual.getUpdatedAt() == now
        assert actual.getUpdatedBy() == "updatedBy"
        assert actual.getVersion() == 0
        assert !actual.isDeleted()
    }

    def "test convertToDto(): passed null" () {
        given:
        def userMapper = Mock(UserMapper)
        def target = new UserRepositoryPersistenceImpl(userMapper)
        when:
        def actual = target.convertToDto(null)
        then:
        assert actual == null
    }

    def "test convertToDto(): passed object" () {
        given:
        def userMapper = Mock(UserMapper)
        def target = new UserRepositoryPersistenceImpl(userMapper)
        def now = LocalDateTime.now()
        def userEntity = new UserEntity(
                1L,
                "account",
                "passwordDigest",
                UserStatus.REGISTERED.name(),
                now,
                "createdBy",
                now,
                "updatedBy",
                0,
                false
        )
        when:
        def actual = target.convertToDto(userEntity)
        then:
        assert Optional.ofNullable(actual).isPresent()
        assert actual.getId() == 1L
        assert actual.getAccount() == "account"
        assert actual.getPasswordDigest() == "passwordDigest"
        assert actual.getStatus() == UserStatus.REGISTERED
        assert actual.getCreatedAt() == now
        assert actual.getCreatedBy() == "createdBy"
        assert actual.getUpdatedAt() == now
        assert actual.getUpdatedBy() == "updatedBy"
        assert actual.getVersion() == 0
        assert !actual.isDeleted()
    }

}
