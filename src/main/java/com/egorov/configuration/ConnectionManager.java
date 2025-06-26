package com.egorov.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final HikariDataSource DATA_SOURCE;

    static {
        Properties props = JDBCConfig.PROPERTIES;
        HikariConfig config = new HikariConfig(props);
        DATA_SOURCE = new HikariDataSource(config);
    }

    public static Connection get() throws SQLException {

        try {
            System.out.println("Получено соединение");
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
