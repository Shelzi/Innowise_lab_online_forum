package com.innowise.onlineforum.model.dao.daoimpl;

import com.innowise.onlineforum.model.dao.UserDao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserDaoImpl implements UserDao {

    private static volatile UserDaoImpl instance = UserDaoImpl.getInstance();
    private static final Lock locker = new ReentrantLock();


    public static UserDaoImpl getInstance() {
        if (instance == null) {
            locker.lock();
            if (instance == null) {
                instance = new UserDaoImpl();
            }
            locker.unlock();
        }
        return instance;
    }
}
