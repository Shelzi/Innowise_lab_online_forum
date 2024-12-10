package com.innowise.onlineforum.model.service.serviceimpl;

import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.dao.UserDao;
import com.innowise.onlineforum.model.dao.daoimpl.UserDaoImpl;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.entity.UserCredentials;
import com.innowise.onlineforum.model.factory.EntityFactory;
import com.innowise.onlineforum.model.factory.UserFactory;
import com.innowise.onlineforum.model.service.UserService;
import com.innowise.onlineforum.util.MessageManagerUtil;
import com.innowise.onlineforum.util.PasswordUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceImpl implements UserService {
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final EntityFactory<User> userFactory = UserFactory.getInstance();
    private static volatile UserServiceImpl instance = UserServiceImpl.getInstance();
    private static Lock locker;

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            locker = new ReentrantLock();
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
            Optional<User> userOptional = userFactory.create(fields);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (userDao.isEmailAvailable(user.getEmail())) {
                    String password = fields.get(RequestParameter.PASSWORD);
                    String hashedPassword = PasswordUtil.hashPassword(password);
                    UserCredentials credentials = UserCredentials.builder().passwordHash(hashedPassword).build();
                    credentials.setUser(user);
                    user.setCredentials(credentials);
                    result = userDao.save(user);
                } else {
                    fields.put(RequestParameter.EMAIL, MessageManagerUtil.getProperty("message.emailInUse"));
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        try {
            Optional<String> hashedPasswordOptional = userDao.findPasswordByEmail(email);
            Optional<User> userOptional = Optional.empty();
            if (hashedPasswordOptional.isPresent()
                    && PasswordUtil.checkPassword(password, hashedPasswordOptional.get())) {
                userOptional = userDao.findByEmail(email);
            }
            return userOptional;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}