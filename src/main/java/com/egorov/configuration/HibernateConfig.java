package com.egorov.configuration;

import com.egorov.model.*;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;


public class HibernateConfig {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperties(loadHibernateProperties());
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(AcquiringBank.class);
            configuration.addAnnotatedClass(Card.class);
            configuration.addAnnotatedClass(CardStatus.class);
            configuration.addAnnotatedClass(Currency.class);
            configuration.addAnnotatedClass(IssuingBank.class);
            configuration.addAnnotatedClass(MerchantCategoryCode.class);
            configuration.addAnnotatedClass(PaymentSystem.class);
            configuration.addAnnotatedClass(ResponseCode.class);
            configuration.addAnnotatedClass(SalesPoint.class);
            configuration.addAnnotatedClass(Terminal.class);
            configuration.addAnnotatedClass(Transaction.class);
            configuration.addAnnotatedClass(TransactionType.class);
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }

    private static Properties loadHibernateProperties() {
        Properties properties = new Properties();
        try (InputStream input = HibernateConfig.class.getClassLoader().getResourceAsStream("hibernate.properties")) {
            if (input == null) {
                throw new RuntimeException();
            }
            properties.load(input);
            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}