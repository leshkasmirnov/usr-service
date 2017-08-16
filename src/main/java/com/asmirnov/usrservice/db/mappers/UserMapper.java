package com.asmirnov.usrservice.db.mappers;

import com.asmirnov.usrservice.core.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017
 */
public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new User(r.getLong("id"),
                r.getString("username"),
                r.getString("first_name"),
                r.getString("last_name"),
                r.getString("email"),
                r.getString("password"),
                r.getString("phone"),
                r.getInt("user_status"));
    }
}
