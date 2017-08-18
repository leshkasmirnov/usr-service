package com.asmirnov.usrservice.db;

import com.asmirnov.usrservice.core.AccessToken;
import com.asmirnov.usrservice.db.mappers.AccessTokenMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
@RegisterMapper(AccessTokenMapper.class)
public interface AccessTokenDao {

    @SqlUpdate("insert into access_token (access_token, user_id, last_access) values (:accessToken, :userId, :lastAccess)")
    void insertAccessToken(@Bind("accessToken") String accessToken, @Bind("userId") Long userId, @Bind("lastAccess") Date lastAccess);

    @SqlQuery("select * from access_token where user_id = :userId")
    AccessToken getAccessTokenByUserId(@Bind("userId") Long userId);

}
