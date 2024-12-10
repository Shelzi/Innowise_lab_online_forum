package com.innowise.onlineforum.model.factory;

import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.entity.UserRole;
import com.innowise.onlineforum.validation.UserValidator;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class UserFactory implements EntityFactory<User> {
    private static final Lock locker = new ReentrantLock();
    private static EntityFactory<User> instance;

    private UserFactory() {
    }

    public static EntityFactory<User> getInstance() {
        if (instance == null) {
            locker.lock();
            if (instance == null) {
                instance = new UserFactory();
            }
            locker.unlock();
        }
        return instance;
    }

    @Override
    public Optional<User> create(Map<String, String> fields) {
        Optional<User> result = Optional.empty();
        if (UserValidator.isRegisterFormValid(fields)) {
            String email = fields.get(RequestParameter.EMAIL);
            String username = fields.get(RequestParameter.USER_NAME);
            UserRole userRole = UserRole.valueOf(fields.get(RequestParameter.USER_ROLE).toUpperCase(Locale.ROOT));
            result = Optional.of(User.builder().email(email).username(username).userRole(userRole).build());
        }
        return result;
    }
}
