package io.spring.start.sample.hexagon.core.services

import io.spring.start.sample.hexagon.core.dtos.User
import io.spring.start.sample.hexagon.core.enums.UserStatus
import io.spring.start.sample.hexagon.core.repositories.UserRepository
import org.apache.commons.codec.digest.DigestUtils
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class UserServiceTest extends Specification {

    def "test register(): failed, password can not confirm"() {
        given:
          // mock
          def repository = Mock(UserRepository)
          0 * repository.register(_)
          // test target
          def target = new UserService(repository)
        when:
          target.register("account", "password", "pass")
        then:
          def actual = thrown(RuntimeException)
          actual.getMessage() == "Can not confirm password."
    }

    @Unroll
    def "test register(): success, #testCase"() {
        given:
          // mock
          def repository = Mock(UserRepository)
          1 * repository.register({
              def arg = it as User
              assert arg.getId() == null
              assert arg.getAccount() == account
              assert arg.getPasswordDigest() == passwordDigest
              assert arg.getStatus() == UserStatus.REGISTERED
              assert arg.getCreatedAt() != null
              assert arg.getCreatedBy() == account
              assert arg.getUpdatedAt() != null
              assert arg.getUpdatedBy() == account
              assert arg.getVersion() == 0
              assert !arg.isDeleted()
              return true
          }) >> new User(
                1L,
                account,
                passwordDigest,
                UserStatus.REGISTERED,
                LocalDateTime.now(),
                account,
                LocalDateTime.now(),
                account,
                0,
                false
          )
          // test target
          def target = new UserService(repository)
        when:
          def actual = target.register(account, password, password)
        then:
          assert actual.getId() == 1L
          assert actual.getAccount() == account
          assert actual.getPasswordDigest() == passwordDigest
          assert actual.getStatus() == UserStatus.REGISTERED
          assert actual.getCreatedBy() == account
          assert actual.getUpdatedBy() == account
        where:
          testCase      | account   | password     | passwordDigest
          "test case 1" | "account" | "password"   | DigestUtils.sha256Hex("password")
          "test case 2" | "email"   | "passphrase" | DigestUtils.sha256Hex("passphrase")
    }

    def "test getUser(): success."() {
        given:
          // mock response
          def res = Optional.of(new User(
                  id,
                  "account1",
                  "passwordDigest",
                  status,
                  LocalDateTime.now(),
                  "account1",
                  LocalDateTime.now(),
                  "account1",
                  0,
                  false
          ))
          // mock
          def repository = Mock(UserRepository)
          1 * repository.getUser({
              def arg = it as Long
              assert arg == id
              return true
          }, {
              it as UserStatus
              assert it == status
              return true
          }) >> res
          // test target
          def target = new UserService(repository)
        when:
          def actual = target.getUser(id, status)
        then:
          assert actual.isPresent()
          assert actual.get().getId() == id
          assert actual.get().getStatus() == status
        where:
        id | status
        1L | UserStatus.REGISTERED
        2L | UserStatus.ACTIVATE
    }

    def "test getNewRegisteredUsers(): success."() {
        given:
          // mock response
          def res = [
                  new User(
                          1L,
                          "account1",
                          "passwordDigest",
                          UserStatus.REGISTERED,
                          LocalDateTime.now(),
                          "account1",
                          LocalDateTime.now(),
                          "account1",
                          0,
                          false
                  ),
                  new User(
                          2L,
                          "account2",
                          "passwordDigest",
                          UserStatus.REGISTERED,
                          LocalDateTime.now(),
                          "account2",
                          LocalDateTime.now(),
                          "account2",
                          1,
                          true
                  )
          ]
          // mock
          def repository = Mock(UserRepository)
          1 * repository.getNewRegisteredUsers() >> res
          // test target
          def target = new UserService(repository)
        when:
          // test
          def actual = target.getNewRegisteredUsers()
        then:
          actual.size() == 2
          actual.get(0).getId() == 1L
          actual.get(0).getAccount() == "account1"
          actual.get(0).getPasswordDigest() == "passwordDigest"
          actual.get(0).getStatus() == UserStatus.REGISTERED
          actual.get(0).getCreatedBy() == "account1"
          actual.get(0).getUpdatedBy() == "account1"
          actual.get(0).getVersion() == 0
          !actual.get(0).isDeleted()
          actual.get(1).getId() == 2L
          actual.get(1).getAccount() == "account2"
          actual.get(1).getPasswordDigest() == "passwordDigest"
          actual.get(1).getStatus() == UserStatus.REGISTERED
          actual.get(1).getCreatedBy() == "account2"
          actual.get(1).getUpdatedBy() == "account2"
          actual.get(1).getVersion() == 1
          actual.get(1).isDeleted()
    }

}
