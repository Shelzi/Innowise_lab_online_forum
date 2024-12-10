package com.innowise.onlineforum.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum UserRole {
    AUTHORIZED(1),
    ADMIN(2),
    GUEST(3);

    private final int roleId;

    public static Optional<UserRole> valueOfRoleId(int roleId) {
        for (UserRole e : values()) {
            if (e.roleId == roleId) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }
}