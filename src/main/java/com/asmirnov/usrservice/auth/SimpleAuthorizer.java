package com.asmirnov.usrservice.auth;

import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.db.DaoProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.Authorizer;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 17/08/2017.
 */
@Singleton
public class SimpleAuthorizer implements Authorizer<User> {

    @Inject
    public SimpleAuthorizer(DaoProvider daoProvider) {
    }

    @Override
    public boolean authorize(User principal, String role) {
        return "ADMIN".equals(role);
    }
}
