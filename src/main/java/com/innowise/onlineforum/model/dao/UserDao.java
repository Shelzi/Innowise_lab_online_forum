package com.innowise.onlineforum.model.dao;

import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean save(User user) throws DaoException;

    void update(User user) throws DaoException;

    void delete(User user) throws DaoException;

    Optional<User> findById(Long id) throws DaoException;

    List<User> findAll() throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    boolean isEmailAvailable(String email) throws DaoException;

    Optional<String> findPasswordByEmail(String email) throws DaoException;

    boolean isUsernameAvailable(String username) throws DaoException;
}
