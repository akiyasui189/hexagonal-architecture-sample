package io.spring.start.sample.hexagon.core.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.spring.start.sample.hexagon.core.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    final private String account;
    @JsonIgnore
    final private String passwordDigest;
    final private UserStatus status;
    final private LocalDateTime createdAt;
    final private String createdBy;
    final private LocalDateTime updatedAt;
    final private String updatedBy;
    final private int version;
    final private boolean deleted;
}
