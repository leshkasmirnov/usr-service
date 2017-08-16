package com.asmirnov.usrservice.db;

import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.db.mappers.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017
 */
@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlQuery("select * from usr where username = :name")
    User getByName(@Bind("name") String name);

    @SqlUpdate("insert into usr (username, first_name, last_name, email, password, phone, user_status) values (:username, :firstName, :lastName, :email, :password, :phone, :userStatus)")
    void insertUser(@Bind("username") String username, @Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("email") String email, @Bind("password") String password, @Bind("phone") String phone, @Bind("userStatus") Integer userStatus);

    @SqlUpdate("delete from usr where username = :username")
    void removeByName(@Bind("username") String username);

    @SqlUpdate("update usr set username = :username, first_name = :firstName, last_name = :lastName, email = :email, password = :password, phone = :phone, user_status = :userStatus where username = :name")
    void updateByName(@Bind("name") String name, @Bind("username") String username, @Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("email") String email, @Bind("password") String password, @Bind("phone") String phone, @Bind("userStatus") Integer userStatus);
}
