package com.asmirnov.usrservice.auth;

import com.asmirnov.usrservice.core.Role;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.db.DaoProvider;
import com.asmirnov.usrservice.db.RoleDao;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.Authorizer;

import java.util.Collection;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 17/08/2017.
 */
@Singleton
public class SimpleAuthorizer implements Authorizer<User> {

    private final RoleDao roleDao;

    @Inject
    public SimpleAuthorizer(DaoProvider daoProvider) {
        roleDao = daoProvider.getDao(RoleDao.class);
    }

    @Override
    public boolean authorize(User principal, String role) {
        Collection<Role> userRoles = roleDao.getUserRoles(principal.getId());
        return userRoles.stream().anyMatch(r -> role.equals(r.getName()));
    }
}
