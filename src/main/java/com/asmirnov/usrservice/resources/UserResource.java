package com.asmirnov.usrservice.resources;

import com.asmirnov.usrservice.api.UserModel;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.service.UserService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.asmirnov.usrservice.util.ApiUtil.userFromUserModel;
import static com.asmirnov.usrservice.util.ApiUtil.userModelFromUser;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{name}")
    public Response getUserByName(@PathParam("name") @NotNull String name) {
        return doActionWithCheckName(name, null, (username, usr) -> {
        }, true);
    }

    @POST
    public Response createUser(@NotNull @Valid UserModel user) {
        try {
            userService.createUser(userFromUserModel(user));
        } catch (Exception e) {
            LOG.error("Error in handle request", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/{name}")
    public Response updateUser(@PathParam("name") @NotNull String name, @NotNull @Valid UserModel user) {
        return doActionWithCheckName(name, user, userService::updateUserByName, false);
    }

    @DELETE
    @Path("/{name}")
    public Response deleteUserByName(@PathParam("name") @NotNull String name) {
        return doActionWithCheckName(name, null, (username, usr) -> userService.removeByName(username), false);
    }

    private Response doActionWithCheckName(String username, UserModel user, BiConsumer<String, User> consumer, boolean putFoundUser) {
        if (checkName(username)) {
            User foundUser = userService.getUserByName(username);
            if (foundUser != null) {
                try {
                    consumer.accept(username, userFromUserModel(user));
                    Object entity = null;
                    if (putFoundUser) {
                        entity = userModelFromUser(foundUser);
                    }
                    return Response.ok().entity(entity).build();
                } catch (Exception e) {
                    LOG.error("Error in handle request", e);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private boolean checkName(String name) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
