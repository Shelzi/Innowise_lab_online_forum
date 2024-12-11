package com.innowise.onlineforum.model.dao.daoimpl;

import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.model.dao.TopicDao;
import com.innowise.onlineforum.model.entity.Topic;
import com.innowise.onlineforum.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TopicDaoImpl implements TopicDao {
    private static volatile TopicDaoImpl instance = TopicDaoImpl.getInstance();
    private static Lock locker;

    private TopicDaoImpl() {
    }

    public static TopicDaoImpl getInstance() {
        if (instance == null) {
            locker = new ReentrantLock();
            locker.lock();
            if (instance == null) {
                instance = new TopicDaoImpl();
            }
            locker.unlock();
        }
        return instance;
    }

    @Override
    public List<Topic> findAll() throws DaoException {
        List<Topic> topics;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Topic> query = session.createQuery("FROM Topic ORDER BY createdAt DESC", Topic.class);
            topics = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Error when retrieving the topics list", e);
        }
        return topics;
    }

    @Override
    public List<Topic> findByCategory(String category) throws DaoException {
        List<Topic> topics;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Topic> query = session.createQuery(
                    "FROM Topic WHERE category = :category ORDER BY createdAt DESC", Topic.class);
            query.setParameter("category", category);
            topics = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Error when retrieving tops by category", e);
        }
        return topics;
    }

    @Override
    public Optional<Topic> findById(Long id) throws DaoException {
        Optional<Topic> topicOptional;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Topic topic = session.get(Topic.class, id);
            topicOptional = Optional.ofNullable(topic);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Error when searching for a topic by ID", e);
        }
        return topicOptional;
    }
}
