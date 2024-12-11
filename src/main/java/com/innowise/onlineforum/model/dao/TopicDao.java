package com.innowise.onlineforum.model.dao;

import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.model.entity.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicDao {
    List<Topic> findAll() throws DaoException;

    List<Topic> findByCategory(String category) throws DaoException;

    Optional<Topic> findById(Long id) throws DaoException;
}
