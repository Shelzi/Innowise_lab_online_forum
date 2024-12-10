package com.innowise.onlineforum.model.dao.daoimpl;

import com.innowise.onlineforum.exception.DaoException;
import com.innowise.onlineforum.model.dao.UserDao;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.util.HibernateUtil;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserDaoImpl implements UserDao {

    private static volatile UserDaoImpl instance = UserDaoImpl.getInstance();
    private static Lock locker;

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            locker = new ReentrantLock();
            locker.lock();
            if (instance == null) {
                instance = new UserDaoImpl();
            }
            locker.unlock();
        }
        return instance;
    }

    @Override
    public boolean save(User user) throws DaoException {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException("Error saving user", e);
        }
    }

    @Override
    public void update(User user) throws DaoException {

    }

    @Override
    public void delete(User user) throws DaoException {

    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws DaoException {
        return List.of();
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                User user = session.createQuery(
                                "FROM User u WHERE u.email = :email", User.class)
                        .setParameter("email", email)
                        .getSingleResult();
                return Optional.of(user);
            } catch (NoResultException e) {
                return Optional.empty();
            }
        } catch (HibernateException e) {
            throw new DaoException("Error finding user by email", e);
        }
    }

    @Override
    public boolean isEmailAvailable(String email) throws DaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count == 0;
        } catch (HibernateException e) {
            throw new DaoException("Error checking email availability", e);
        }
    }

    @Override
    public Optional<String> findPasswordByEmail(String email) throws DaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                String passwordHash = session.createQuery(
                                "SELECT uc.passwordHash FROM UserCredentials uc WHERE uc.user.email = :email", String.class)
                        .setParameter("email", email)
                        .getSingleResult();
                return Optional.of(passwordHash);
            } catch (NoResultException e) {
                return Optional.empty();
            }
        } catch (HibernateException e) {
            throw new DaoException("Error finding password by email", e);
        }
    }

    @Override
    public boolean isUsernameAvailable(String username) throws DaoException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return count == 0;
        } catch (HibernateException e) {
            throw new DaoException("Error checking username availability", e);
        }
    }
}
