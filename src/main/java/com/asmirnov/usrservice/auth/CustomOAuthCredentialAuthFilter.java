package com.asmirnov.usrservice.auth;

import com.asmirnov.usrservice.core.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.DefaultUnauthorizedHandler;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by a.smirnov19 on 18/08/2017.
 */
@Singleton
@Priority(Priorities.AUTHENTICATION)
public class CustomOAuthCredentialAuthFilter<P extends Principal> extends AuthFilter<String, User> {

    /**
     * Query parameter used to pass Bearer token
     *
     * @see <a href="https://tools.ietf.org/html/rfc6750#section-2.3">The OAuth 2.0 Authorization Framework: Bearer Token Usage</a>
     */
    public static final String OAUTH_ACCESS_TOKEN_PARAM = "access_token";

    @Inject
    public CustomOAuthCredentialAuthFilter(TokenAuthenticator tokenAuthenticator, SimpleAuthorizer simpleAuthorizer) {
        authenticator = tokenAuthenticator;
        authorizer = simpleAuthorizer;
        prefix = "auth_filter";
        realm = "realm";
        unauthorizedHandler = new DefaultUnauthorizedHandler();
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        String credentials = getCredentials(requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        // If Authorization header is not used, check query parameter where token can be passed as well
        if (credentials == null) {
            credentials = requestContext.getUriInfo().getQueryParameters().getFirst(OAUTH_ACCESS_TOKEN_PARAM);
        }

        if (!authenticate(requestContext, credentials, SecurityContext.BASIC_AUTH)) {
            throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
        }
    }

    /**
     * Parses a value of the `Authorization` header in the form of `Bearer a892bf3e284da9bb40648ab10`.
     *
     * @param header the value of the `Authorization` header
     * @return a token
     */
    @Nullable
    private String getCredentials(String header) {
        if (header == null) {
            return null;
        }

        final int space = header.indexOf(' ');
        if (space <= 0) {
            return null;
        }

        final String method = header.substring(0, space);
        if (!prefix.equalsIgnoreCase(method)) {
            return null;
        }

        return header.substring(space + 1);
    }
}
