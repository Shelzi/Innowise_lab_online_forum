package com.innowise.onlineforum.model.service.serviceimpl;

import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.dao.TopicDao;
import com.innowise.onlineforum.model.dao.UserDao;
import com.innowise.onlineforum.model.dao.daoimpl.TopicDaoImpl;
import com.innowise.onlineforum.model.dao.daoimpl.UserDaoImpl;
import com.innowise.onlineforum.model.entity.Topic;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.factory.EntityFactory;
import com.innowise.onlineforum.model.factory.TopicFactory;
import com.innowise.onlineforum.model.service.TopicService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TopicServiceImpl implements TopicService {
    private static volatile TopicServiceImpl instance = TopicServiceImpl.getInstance();
    private static final TopicDao topicDao = TopicDaoImpl.getInstance();
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private final EntityFactory<Topic> topicFactory = TopicFactory.getInstance();

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
            throw new ServiceException("Error when receiving all topics", e);
        }
    }

    @Override
    public List<Topic> getTopicsByCategory(String category) throws ServiceException {
        try {
            return topicDao.findByCategory(category);
        } catch (DaoException e) {
            throw new ServiceException("Error when retrieving topics by category", e);
        }
    }

    @Override
    public Optional<Topic> getTopicById(Long id) throws ServiceException {
        try {
            return topicDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error when searching for a topic by ID", e);
        }
    }

    @Override
    public boolean createTopic(Map<String, String> fields) throws ServiceException {
        boolean result = false;
        try {
            Optional<Topic> topicOptional = topicFactory.create(fields);
            if (topicOptional.isPresent()) {
                Topic topic = topicOptional.get();
                String userIdStr = fields.get("userId");
                if (userIdStr == null || userIdStr.trim().isEmpty()) {
                    fields.put("userId", "User ID is missing.");
                    return false;
                }
                Long userId = Long.parseLong(userIdStr);
                Optional<User> userOpt = userDao.findById(userId);
                if (userOpt.isEmpty()) {
                    fields.put("userId", "User not found.");
                    return false;
                }
                User user = userOpt.get();
                topic.setUser(user);
                topic.setCreatedAt(LocalDateTime.now());
                topic.setRating(0);
                topic.setPinned(false);
                result = topicDao.createTopic(topic);
            }
        } catch (NumberFormatException e) {
            fields.put("userId", "Invalid User ID format.");
            throw new ServiceException("Invalid User ID format.", e);
        } catch (DaoException e) {
            throw new ServiceException("Error creating topic.", e);
        }
        return result;
    }

    @Override
    public boolean deleteTopic(Long topicId, long userId) throws ServiceException {
        try {
            Optional<Topic> topicOpt = topicDao.findById(topicId);
            if (topicOpt.isPresent()) {
                Topic topic = topicOpt.get();
                if (topic.getUser().getId() == userId) {
                    return topicDao.deleteTopic(topicId);
                }
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException("Error deleting topic.", e);
        }
    }

    @Override
    public boolean updateTopic(Map<String, String> fields) throws ServiceException {
        try {
            Optional<Topic> topicOptional = topicFactory.create(fields);
            if (topicOptional.isPresent()) {
                Topic topic = topicOptional.get();
                String topicIdStr = fields.get(RequestParameter.TOPIC_ID);
                if (topicIdStr == null || topicIdStr.trim().isEmpty()) {
                    return false;
                }
                Long topicId = Long.parseLong(topicIdStr);
                Optional<Topic> existingTopicOpt = topicDao.findById(topicId);
                if (existingTopicOpt.isPresent()) {
                    Topic existingTopic = existingTopicOpt.get();
                    existingTopic.setTitle(topic.getTitle());
                    existingTopic.setBody(topic.getBody());
                    existingTopic.setCategory(topic.getCategory());
                    existingTopic.setPinned(Boolean.parseBoolean(fields.get(RequestParameter.TOPIC_PINNED)));
                    existingTopic.setRating(Integer.parseInt(fields.get(RequestParameter.TOPIC_RATING)));
                    return topicDao.updateTopic(existingTopic);
                }
            }
            return false;
        } catch (NumberFormatException | DaoException e) {
            throw new ServiceException("Error updating topic.", e);
        }
    }
}
