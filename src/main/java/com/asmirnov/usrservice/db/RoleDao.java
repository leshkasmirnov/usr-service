package com.asmirnov.usrservice.db;

import com.asmirnov.usrservice.core.Role;
import com.asmirnov.usrservice.db.mappers.RoleMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
@RegisterMapper(RoleMapper.class)
public interface RoleDao {

    @SqlQuery("select r.* from role r join usr_role ur on ur.role_id = r.id join usr u on u.id = ur.user_id where u.id = :userId")
    Collection<Role> getUserRoles(@Bind("userId") Long userId);
}
