
package com.example.OrderService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.example.OrderService.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "orders_keyspace";
    }

    @Override
    protected String getContactPoints() {
        return "localhost";
    }

    @Override
    protected int getPort() {
        return 9042;
    }

    @Override
    public String getLocalDataCenter() {
        return "datacenter1";
    }
}
