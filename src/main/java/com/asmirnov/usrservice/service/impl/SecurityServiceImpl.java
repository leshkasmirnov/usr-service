package com.asmirnov.usrservice.service.impl;

import com.asmirnov.usrservice.core.AccessToken;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.db.AccessTokenDao;
import com.asmirnov.usrservice.db.DaoProvider;
import com.asmirnov.usrservice.db.UserDao;
import com.asmirnov.usrservice.service.SecurityService;
import com.google.inject.Inject;
import org.joda.time.DateTime;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
public class SecurityServiceImpl implements SecurityService {

    private final UserDao userDao;
    private final AccessTokenDao accessTokenDao;

    @Inject
    public SecurityServiceImpl(DaoProvider daoProvider) {
        userDao = daoProvider.getDao(UserDao.class);
        accessTokenDao = daoProvider.getDao(AccessTokenDao.class);
    }

    @Override
    public Optional<AccessToken> login(String username, String password) {
        Optional<User> user = checkUser(username, password);
        if (!user.isPresent()) {
            return Optional.empty();
        }
        AccessToken accessToken = accessTokenDao.getAccessTokenByUserId(user.get().getId());
        if (accessToken != null) {
            return Optional.of(accessToken);
        }
        return generateAccessToken(user.get());
    }

    @Override
    public void logout(User user) {
        accessTokenDao.deleteByUserId(user.getId());
    }

    private Optional<AccessToken> generateAccessToken(User user) {
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(user.getId());
        accessToken.setToken(UUID.randomUUID());
        accessToken.setLastAccess(DateTime.now());
        accessTokenDao.insertAccessToken(accessToken.getToken().toString(), accessToken.getUserId(), accessToken.getLastAccess().toDate());
        return Optional.of(accessToken);
    }

    private Optional<User> checkUser(String username, String password) {
        User user = userDao.getByName(username);
        if (user == null) {
            return Optional.empty();
        }
        if (user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
