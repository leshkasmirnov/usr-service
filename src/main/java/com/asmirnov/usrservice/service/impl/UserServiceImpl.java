package com.asmirnov.usrservice.service.impl;

import com.asmirnov.usrservice.db.UserDao;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.service.UserService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.skife.jdbi.v2.DBI;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017
 */
@Singleton
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Inject
    public UserServiceImpl(DBI dbi) {
        userDao = dbi.onDemand(UserDao.class);
    }

    @Override
    public User getUserByName(String userName) {
        return userDao.getByName(userName);
    }

    @Override
    public void createUser(User user) {
        userDao.insertUser(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhone(), 1);
    }

    @Override
    public void updateUserByName(String username, User user) {
        userDao.updateByName(username, user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhone(), user.getUserStatus());
    }

    @Override
    public void removeByName(String username) {
        userDao.removeByName(username);
    }
}
