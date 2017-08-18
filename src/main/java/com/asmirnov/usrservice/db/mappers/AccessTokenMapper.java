package com.asmirnov.usrservice.db.mappers;

import com.asmirnov.usrservice.core.AccessToken;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
public class AccessTokenMapper implements ResultSetMapper<AccessToken> {
    @Override
    public AccessToken map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        AccessToken accessToken = new AccessToken();
        accessToken.setToken(UUID.fromString(r.getString("token")));
        accessToken.setUserId(r.getLong("user_id"));
        accessToken.setLastAccess(new DateTime(r.getTimestamp("last_access")));

        try {
            r.findColumn("username");
            accessToken.setUser(new UserMapper().map(index, r, ctx));
        } catch (SQLException e) {
            //do nothing
        }
        return accessToken;
    }
}
