package com.asmirnov.usrservice.service.impl;

import com.asmirnov.usrservice.core.AccessToken;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.db.AccessTokenDao;
import com.asmirnov.usrservice.db.UserDao;
import com.asmirnov.usrservice.service.SecurityService;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
public class SecurityServiceImpl implements SecurityService {

    private final UserDao userDao;
    private final AccessTokenDao accessTokenDao;

    public SecurityServiceImpl(DBI dbi) {
        userDao = dbi.onDemand(UserDao.class);
        accessTokenDao = dbi.onDemand(AccessTokenDao.class);
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
    public void logout(String username) {

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
