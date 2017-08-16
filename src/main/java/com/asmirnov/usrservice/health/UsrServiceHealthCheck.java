package com.asmirnov.usrservice.health;

import com.google.inject.Singleton;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017
 */
@Singleton
@SuppressWarnings("unused")
public class UsrServiceHealthCheck extends InjectableHealthCheck {

    @Override
    public String getName() {
        return "123";
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
