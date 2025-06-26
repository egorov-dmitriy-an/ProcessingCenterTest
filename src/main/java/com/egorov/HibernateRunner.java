package com.egorov;

import com.egorov.configuration.HibernateConfig;
import com.egorov.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HibernateRunner {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            System.out.println(sessionFactory);
            System.out.println("___________");

            session.beginTransaction();

            //        Создадим статусы карт
            CardStatus cardStatus = CardStatus.builder()
                    .cardStatusName("Card is valid.")
                    .build();

            CardStatus cardStatus2 = CardStatus.builder()
                    .cardStatusName("Card is lost.")
                    .build();
            session.persist(cardStatus);
            session.persist(cardStatus2);

            //        Создадим платежные системы
            PaymentSystem paymentSystem = PaymentSystem.builder()
                    .paymentSystemName("MasterCard")
                    .build();

            PaymentSystem paymentSystem2 = PaymentSystem.builder()
                    .paymentSystemName("VISA")
                    .build();

            session.persist(paymentSystem);
            session.persist(paymentSystem2);

            //        Создадим валюты
            Currency currency = Currency.builder()
                    .currencyDigitalCode("643")
                    .currencyLetterCode("RUB")
                    .currencyName("Russian Ruble")
                    .build();

            Currency currency2 = Currency.builder()
                    .currencyDigitalCode("840")
                    .currencyLetterCode("USD")
                    .currencyName("US Dollar")
                    .build();

            session.persist(currency);
            session.persist(currency2);

            //        Создадим банки
            IssuingBank issuingBank = IssuingBank.builder()
                    .bic("041234569")
                    .bin("12345")
                    .abbreviatedName("ПАО - Банк-эмитент №1")
                    .build();

            IssuingBank issuingBank2 = IssuingBank.builder()
                    .bic("041234585")
                    .bin("12376")
                    .abbreviatedName("ПАО - Банк-эмитент №2")
                    .build();

            session.persist(issuingBank);
            session.persist(issuingBank2);

            //        Создадим аккаунты
            Account account = Account.builder()
                    .accountNumber("40817810800000000001")
                    .balance(BigDecimal.valueOf(649.7))
                    .currencyId(currency)
                    .issuingBankId(issuingBank)
                    .build();

            Account account2 = Account.builder()
                    .accountNumber("40817810800000000002")
                    .balance(BigDecimal.valueOf(48702.7))
                    .currencyId(currency2)
                    .issuingBankId(issuingBank2)
                    .build();

            session.persist(account);
            session.persist(account2);

            //        Создадим карты
            Card card1 = Card.builder()
                    .cardNumber("41034500000000000020")
                    .expirationDate(LocalDate.of(2024, 10, 30))
                    .holderName("Иван Иванов")
                    .cardStatusId(cardStatus)
                    .paymentSystemId(paymentSystem)
                    .accountId(account)
                    .receivedFromIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175))
                    .sentToIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271))
                    .build();

            Card card2 = Card.builder()
                    .cardNumber("41034500000000000021")
                    .expirationDate(LocalDate.of(2024, 9, 25))
                    .holderName("Мария Петрова")
                    .cardStatusId(cardStatus2)
                    .paymentSystemId(paymentSystem2)
                    .accountId(account2)
                    .receivedFromIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175))
                    .sentToIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271))
                    .build();

            Card card3 = Card.builder()
                    .cardNumber("41034500000000000022")
                    .expirationDate(LocalDate.of(2024, 8, 20))
                    .holderName("Алексей Смирнов")
                    .cardStatusId(cardStatus)
                    .paymentSystemId(paymentSystem2)
                    .accountId(account)
                    .receivedFromIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175))
                    .sentToIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271))
                    .build();

            Card card4 = Card.builder()
                    .cardNumber("41034500000000000023")
                    .expirationDate(LocalDate.of(2024, 7, 15))
                    .holderName("Елена Кузнецова")
                    .cardStatusId(cardStatus2)
                    .paymentSystemId(paymentSystem)
                    .accountId(account2)
                    .receivedFromIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175))
                    .sentToIssuingBank(LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271))
                    .build();


            session.persist(card1);
            session.persist(card2);
            session.persist(card3);
            session.persist(card4);

            session.getTransaction().commit();
        }
    }
}
