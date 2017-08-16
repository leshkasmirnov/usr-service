package com.asmirnov.usrservice.service;

import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.service.impl.UserServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {

    User getUserByName(String userName);

    void createUser(User user);

    void updateUserByName(String username, User user);

    void removeByName(String username);
}
