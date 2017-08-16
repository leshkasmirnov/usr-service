package com.asmirnov.usrservice;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017.
 */
public class UsrServiceApp extends Application<UsrServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new UsrServiceApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<UsrServiceConfiguration> bootstrap) {
        GuiceBundle<UsrServiceConfiguration> guiceBundle = GuiceBundle.<UsrServiceConfiguration>newBuilder()
                .addModule(new MainModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(UsrServiceConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new MigrationsBundle<UsrServiceConfiguration>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(UsrServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(UsrServiceConfiguration configuration, Environment environment) throws Exception {
    }
}
