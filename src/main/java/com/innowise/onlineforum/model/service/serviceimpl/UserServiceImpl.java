package com.innowise.onlineforum.model.service.serviceimpl;

import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.dao.UserDao;
import com.innowise.onlineforum.model.dao.daoimpl.UserDaoImpl;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.service.UserService;
import com.innowise.onlineforum.util.PasswordUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceImpl implements UserService {
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static volatile UserServiceImpl instance = UserServiceImpl.getInstance();
    private static final Lock locker = new ReentrantLock();

    private static final String EMAIL_ALREADY_IN_USE = "Email is already in use";
    private static final short MIN_PASS_LENGTH = 6;

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            locker.lock();
            if (instance == null) {
                instance = new UserServiceImpl();
            }
            locker.unlock();
        }
        return instance;
    }

    @Override
    public boolean create(Map<String, String> fields) throws ServiceException {
        boolean result = false;
        try {
            validateUserData(fields);
            String email = fields.get("email");
            String password = fields.get("password");
            Optional<User> existingUser = userDao.findByEmail(email);
            if (existingUser.isPresent()) {
                fields.put(RequestParameter.EMAIL, "Email already taken");
            }
            String hashedPassword = PasswordUtil.hashPassword(password);
            User user = new User(username, hashedPassword);
            userDAO.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        try {
            Optional<String> resultPasswordOptional = userDao.findPasswordByEmail(email);
            Optional<User> userOptional = Optional.empty();
            if (resultPasswordOptional.isPresent() && Encryptor.check(password, resultPasswordOptional.get())) {
                userOptional = userDao.findUserByEmail(email);
            }
            return userOptional;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    // Метод валидации данных пользователя
    private void validateUserData(Map<String, String> fields) throws ServiceException {

        String email = fields.get("email");
        String username = fields.get("username");
        String password = fields.get("password");

        if (username == null || username.isEmpty() || username.length() <) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.length() < MIN_PASS_LENGTH) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        // Дополнительные проверки (например, регулярные выражения)
    }
}