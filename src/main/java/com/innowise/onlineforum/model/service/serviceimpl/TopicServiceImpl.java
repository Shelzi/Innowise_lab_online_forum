package com.innowise.onlineforum.model.service.serviceimpl;

import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.dao.TopicDao;
import com.innowise.onlineforum.model.dao.daoimpl.TopicDaoImpl;
import com.innowise.onlineforum.model.entity.Topic;
import com.innowise.onlineforum.model.service.TopicService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TopicServiceImpl implements TopicService {
    private static volatile TopicServiceImpl instance = TopicServiceImpl.getInstance();
    private static final TopicDao topicDao = TopicDaoImpl.getInstance();
    private static Lock locker;

    public static TopicServiceImpl getInstance() {
        if (instance == null) {
            locker = new ReentrantLock();
            locker.lock();
            if (instance == null) {
                instance = new TopicServiceImpl();
            }
            locker.unlock();
        }
        return instance;
    }

    @Override
    public List<Topic> getAllTopics() throws ServiceException {
        try {
            return topicDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при получении всех топиков", e);
        }
    }

    @Override
    public List<Topic> getTopicsByCategory(String category) throws ServiceException {
        try {
            return topicDao.findByCategory(category);
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при получении топиков по категории", e);
        }
    }

    @Override
    public Optional<Topic> getTopicById(Long id) throws ServiceException {
        try {
            return topicDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при поиске топика по ID", e);
        }
    }
}
