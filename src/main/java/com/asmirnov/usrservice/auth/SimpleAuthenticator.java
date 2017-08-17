package com.asmirnov.usrservice.auth;

import com.asmirnov.usrservice.core.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

/**
 * Created by a.smirnov19 on 17/08/2017.
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("pwd".equalsIgnoreCase(credentials.getPassword())) {
            return Optional.of(new User());
        } else {
            return Optional.empty();
        }
    }
}
