package com.asmirnov.usrservice.resources;

import com.asmirnov.usrservice.api.UserModel;
import com.asmirnov.usrservice.api.UserToken;
import com.asmirnov.usrservice.core.AccessToken;
import com.asmirnov.usrservice.core.User;
import com.asmirnov.usrservice.service.SecurityService;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Optional;
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
@Api("User API")
public class UserResource {

    private final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final SecurityService securityService;

    @Inject
    public UserResource(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GET
    @Path("/{name}")
    @ApiOperation(value = "Get user by user name")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 200, message = "successful operation", response = UserModel.class)})
    @Timed
    @PermitAll
    public Response getUserByName(@PathParam("name") @NotNull @ApiParam("The name that needs to be fetched. Use user1 for testing") String name,
                                  @QueryParam("access_token") @NotNull String access_token) {
        return doActionWithCheckName(name, null, (username, usr) -> {
        }, true);
    }

    @POST
    @ApiOperation("Create user object")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 200, message = "successful operation")})
    @Timed
    @RolesAllowed("ADMIN")
    public Response createUser(@NotNull @Valid @ApiParam UserModel user,
                               @QueryParam("access_token") @NotNull String access_token) {
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
    @RolesAllowed("ADMIN")
    public Response updateUser(@PathParam("name") @NotNull String name,
                               @NotNull @Valid UserModel user,
                               @QueryParam("access_token") @NotNull String access_token) {
        return doActionWithCheckName(name, user, userService::updateUserByName, false);
    }

    @DELETE
    @Path("/{name}")
    @ApiOperation("Delete user by name")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 200, message = "successful operation")})
    @Timed
    @RolesAllowed("ADMIN")
    public Response deleteUserByName(@PathParam("name") @NotNull String name,
                                     @QueryParam("access_token") @NotNull String access_token) {
        return doActionWithCheckName(name, null, (username, usr) -> userService.removeByName(username), false);
    }

    @GET
    @Path("/login")
    @ApiOperation("Log user into the system")
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid username/password supplied"),
            @ApiResponse(code = 200, message = "successful operation", response = UserToken.class)})
    @Timed
    public Response login(@QueryParam("username") @ApiParam("The user name for login") String username,
                          @QueryParam("password") @ApiParam("The password for login in clear text") String password) {
        Optional<AccessToken> accessToken = securityService.login(username, password);
        if (accessToken.isPresent()) {
            return Response.ok(new UserToken(accessToken.get().getToken().toString())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/logout")
    @ApiOperation("Logs out current logged in user session")
    @Timed
    @PermitAll
    public Response logout(@Context SecurityContext context,
                           @QueryParam("access_token") @NotNull String access_token) {
        if (context.getUserPrincipal() != null) {
            securityService.logout((User) context.getUserPrincipal());
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
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
