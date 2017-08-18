package com.asmirnov.usrservice.auth;

import com.google.inject.Inject;
import io.dropwizard.auth.AuthDynamicFeature;

import javax.ws.rs.ext.Provider;

/**
 * Created by a.smirnov19 on 18/08/2017.
 */
@Provider
public class CustomAuthDynamicFeature extends AuthDynamicFeature {

    @Inject
    public CustomAuthDynamicFeature(CustomOAuthCredentialAuthFilter authFilter) {
        super(authFilter);
    }
}
