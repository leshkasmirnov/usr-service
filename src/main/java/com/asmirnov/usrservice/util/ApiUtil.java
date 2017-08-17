package com.asmirnov.usrservice.util;

import com.asmirnov.usrservice.api.UserModel;
import com.asmirnov.usrservice.core.User;

/**
 * Created by a.smirnov19 on 17/08/2017.
 */
public final class ApiUtil {

    private ApiUtil() {
    }

    public static UserModel userModelFromUser(User user) {
        return user != null ? new UserModel(user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getUserStatus()) : null;
    }

    public static User userFromUserModel(UserModel user) {
        return user != null ? new User(user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getUserStatus()) : null;
    }
}
