package com.innowise.onlineforum.model.service;

import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    List<Topic> getAllTopics() throws ServiceException;

    List<Topic> getTopicsByCategory(String category) throws ServiceException;

    Optional<Topic> getTopicById(Long id) throws ServiceException;
}
