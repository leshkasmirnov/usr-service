package com.asmirnov.usrservice.auth;

import com.google.inject.Singleton;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ext.Provider;

/**
 * Created by a.smirnov19 on 18/08/2017.
 */
@Provider
@Singleton
public class CustomRolesAllowedDynamicFeature extends RolesAllowedDynamicFeature {
}
