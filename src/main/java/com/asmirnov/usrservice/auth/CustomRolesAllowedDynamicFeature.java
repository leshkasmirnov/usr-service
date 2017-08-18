package com.asmirnov.usrservice.auth;

import com.google.inject.Singleton;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ext.Provider;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 18/08/2017.
 */
@Provider
@Singleton
public class CustomRolesAllowedDynamicFeature extends RolesAllowedDynamicFeature {
}
