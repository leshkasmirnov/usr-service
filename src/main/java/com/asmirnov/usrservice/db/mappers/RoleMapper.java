package com.asmirnov.usrservice.db.mappers;

import com.asmirnov.usrservice.core.Role;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
public class RoleMapper implements ResultSetMapper<Role> {
    @Override
    public Role map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Role(r.getLong("id"), r.getString("rolename"));
    }
}
