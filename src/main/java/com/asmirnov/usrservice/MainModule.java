package com.asmirnov.usrservice;

import com.asmirnov.usrservice.db.DbiProvider;
import com.google.inject.AbstractModule;
import org.skife.jdbi.v2.DBI;

/**
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 16/08/2017
 */
public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBI.class).toProvider(DbiProvider.class);
    }
}
