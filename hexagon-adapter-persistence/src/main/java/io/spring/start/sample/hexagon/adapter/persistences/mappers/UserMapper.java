package io.spring.start.sample.hexagon.adapter.persistences.mappers;

import io.spring.start.sample.hexagon.adapter.persistences.entities.UserEntity;
import io.spring.start.sample.hexagon.core.dtos.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * Insert
     */

    @Insert("INSERT INTO users (" +
            "email, password, status, " +
            "created_at, created_by, updated_at, updated_by, " +
            "version, deleted" +
            ") VALUES (" +
            "#{users.account}, " +
            "#{users.passwordDigest}, " +
            "#{users.status}, " +
            "#{users.created_at}, " +
            "#{users.created_by}, " +
            "#{users.updated_at}, " +
            "#{users.updated_by}, " +
            "#{users.version}, " +
            "#{users.deleted} " +
            ")")
    void insert(@Param("user") UserEntity user);

    /**
     * Select
     */

    @Select("SELECT id, email as account, password as passwordDigest, status, " +
            "created_at as createdAt, created_by as createdBy, " +
            "updated_at as updatedAt, updated_by as updatedBy, " +
            "version, deleted " +
            "FROM users " +
            "WHERE deleted = 0 AND id = #{id} AND status = #{status}")
    User findByIdAndStatus(Long id, String status);

    @Select("SELECT id, email as account, password as passwordDigest, status, " +
            "created_at as createdAt, created_by as createdBy, " +
            "updated_at as updatedAt, updated_by as updatedBy, " +
            "version, deleted " +
            "FROM users " +
            "WHERE deleted = 0 AND status = #{status}")
    List<UserEntity> findByStatus(String status);

}
