package com.asmirnov.usrservice.core;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
public class AccessToken {

    private UUID token;

    private Long userId;

    private DateTime lastAccess;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(DateTime lastAccess) {
        this.lastAccess = lastAccess;
    }
}
