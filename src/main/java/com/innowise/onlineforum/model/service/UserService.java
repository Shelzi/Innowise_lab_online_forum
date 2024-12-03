package com.innowise.onlineforum.model.service;

import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean create(Map<String, String> fields) throws ServiceException;
    Optional<User> login(String email, String password) throws ServiceException;
}
