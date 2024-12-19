package com.innowise.onlineforum.model.dao.daoimpl;

import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.model.dao.TopicDao;
import com.innowise.onlineforum.model.entity.Topic;
import com.innowise.onlineforum.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Topic> query = session.createQuery("FROM Topic t JOIN FETCH t.user ORDER BY t.createdAt DESC", Topic.class);
            return query.list();
        } catch (HibernateException e) {
            throw new DaoException("Hibernate error while fetching all topics.", e);
        }
    }

    @Override
    public List<Topic> findByCategory(String category) throws DaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Topic> query = session.createQuery(
                    "FROM Topic WHERE category = :category ORDER BY createdAt DESC", Topic.class);
            query.setParameter("category", category);
            return query.list();
        } catch (HibernateException e) {
            throw new DaoException("Hibernate error while fetching topics by category.", e);
        }
    }

    @Override
    public Optional<Topic> findById(Long id) throws DaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Topic topic = session.get(Topic.class, id);
            return Optional.ofNullable(topic);
        } catch (HibernateException e) {
            throw new DaoException("Hibernate error while fetching topic by ID.", e);
        }
    }

    @Override
    public boolean createTopic(Topic topic) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(topic);
            transaction.commit();
            return true;
        } catch (ConstraintViolationException e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Constraint violation while creating topic.", e);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Hibernate error while creating topic.", e);
        }
    }

    @Override
    public boolean deleteTopic(Long topicId) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Topic topic = session.get(Topic.class, topicId);
            if (topic == null) {
                transaction.rollback();
                return false;
            }
            session.remove(topic);
            transaction.commit();
            return true;
        } catch (ConstraintViolationException e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Constraint violation while deleting topic.", e);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Hibernate error while deleting topic.", e);
        }
    }

    @Override
    public boolean updateTopic(Topic topic) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(topic);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new DaoException("Error updating topic", e);
        }
    }
}
