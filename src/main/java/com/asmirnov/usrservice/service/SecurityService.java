package com.asmirnov.usrservice.service;

import com.asmirnov.usrservice.core.AccessToken;
import com.asmirnov.usrservice.service.impl.SecurityServiceImpl;
import com.google.inject.ImplementedBy;

import java.util.Optional;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
@ImplementedBy(SecurityServiceImpl.class)
public interface SecurityService {

    /**
     * Log user into system or get empty result if username and password is invalid.
     * If user already logged into system return generated access token.
     *
     * @param username
     * @param password
     * @return
     */
    Optional<AccessToken> login(String username, String password);

    /**
     * Log user out from system.
     *
     * @param username
     */
    void logout(String username);
}
