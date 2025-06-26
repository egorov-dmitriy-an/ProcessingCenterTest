package com.egorov.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JDBCConfig {
    public static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private JDBCConfig() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream resourceAsStream =
                     JDBCConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
