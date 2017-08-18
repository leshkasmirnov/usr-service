package com.asmirnov.usrservice.auth;

import com.asmirnov.usrservice.core.AccessToken;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.db.AccessTokenDao;
import com.asmirnov.usrservice.db.DaoProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
@Singleton
public class TokenAuthenticator implements Authenticator<String, User> {

    public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 30;

    private final AccessTokenDao accessTokenDao;

    @Inject
    public TokenAuthenticator(DaoProvider daoProvider) {
        accessTokenDao = daoProvider.getDao(AccessTokenDao.class);
    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        AccessToken foundToken = accessTokenDao.getAccessTokenByToken(token);
        if (foundToken == null) {
            return Optional.empty();
        }

        // Check if the last access time is not too far in the past (the access token is expired)
        Period period = new Period(foundToken.getLastAccess(), new DateTime());
        if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN) {
            return Optional.empty();
        }

        accessTokenDao.updateAccessTokenLastAccess(token, new Date());
        return Optional.of(foundToken.getUser());
    }
}
