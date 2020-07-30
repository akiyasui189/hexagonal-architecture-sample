package io.spring.start.sample.hexagon.adapter.persistences.daos;

import io.spring.start.sample.hexagon.adapter.persistences.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Long> { }