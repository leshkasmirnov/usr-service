package com.asmirnov.usrservice.resources;

import com.asmirnov.usrservice.api.UserModel;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.service.UserService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
@Path("v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
@Api("User API")
public class UserResource {

    private final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/{name}")
    @ApiOperation("Get user by user name")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 200, message = "successful operation", response = UserModel.class)})
    @Timed
    public Response getUserByName(@PathParam("name") @NotNull @ApiParam("The name that needs to be fetched. Use user1 for testing") String name) {
        return doActionWithCheckName(name, null, (username, usr) -> {
        }, true);
    }

    @POST
    @ApiOperation("Create user object")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 200, message = "successful operation")})
    @Timed
    public Response createUser(@NotNull @Valid @ApiParam UserModel user) {
        if (!checkName(user.getUsername())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
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
    @ApiOperation("Update user by name")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 200, message = "successful operation")})
    @Timed
    public Response updateUser(@PathParam("name") @NotNull String name, @NotNull @Valid UserModel user) {
        return doActionWithCheckName(name, user, userService::updateUserByName, false);
    }

    @DELETE
    @Path("/{name}")
    @ApiOperation("Delete user by name")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 200, message = "successful operation")})
    @Timed
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
