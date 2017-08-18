package com.asmirnov.usrservice.db;

import com.asmirnov.usrservice.UsrServiceConfiguration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.ext.Provider;

/**
 * Created by a.smirnov19 on 18/08/2017.
 */
@Singleton
@Provider
public class DaoProvider {

    private final DBI dbi;

    @Inject
    public DaoProvider(UsrServiceConfiguration config, Environment environment) throws ClassNotFoundException {
        this.dbi = new DBIFactory().build(environment, config.getDataSourceFactory(), "postgresql");
    }

    public <SqlObjectType> SqlObjectType getDao(Class<SqlObjectType> daoClass) {
        return dbi.onDemand(daoClass);
    }
}
