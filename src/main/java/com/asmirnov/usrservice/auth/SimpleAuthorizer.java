package com.asmirnov.usrservice.auth;

import com.asmirnov.usrservice.core.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 17/08/2017.
 */
public class SimpleAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User principal, String role) {
        return "ADMIN".equals(role);
    }
}
