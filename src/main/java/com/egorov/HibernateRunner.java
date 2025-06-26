package com.egorov;

import com.egorov.configuration.HibernateConfig;
import com.egorov.model.CardStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            System.out.println(sessionFactory);
            System.out.println("___________");


            session.beginTransaction();

            CardStatus cardStatus = CardStatus.builder()
                    .id(2L)
                    .cardStatusName("Card is valid.")
                    .build();

            session.persist(cardStatus);
            session.getTransaction().commit();

        }
    }
}
