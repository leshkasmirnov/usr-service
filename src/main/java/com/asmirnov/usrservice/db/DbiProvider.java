package com.asmirnov.usrservice.db;

import com.asmirnov.usrservice.UsrServiceConfiguration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017
 */
public class DbiProvider implements Provider<DBI> {

    private final DBI dbi;

    @Inject
    public DbiProvider(Environment environment, UsrServiceConfiguration configuration) {
        this.dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "postgresql");
    }

    @Override
    public DBI get() {
        return this.dbi;
    }
}
