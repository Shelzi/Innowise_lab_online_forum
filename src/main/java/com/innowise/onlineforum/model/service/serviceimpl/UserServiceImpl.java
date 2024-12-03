package com.innowise.onlineforum.model.service.serviceimpl;

import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.service.UserService;

import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Override
    public boolean create(Map<String, String> fields) throws ServiceException {
        return false;
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
}
