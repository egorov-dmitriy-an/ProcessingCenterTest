package com.egorov;

import com.egorov.dao.jdbc.*;
import com.egorov.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JdbcRunner {
    public static void main(String[] args) {
//        Создадим Dao
        CardStatusJDBCDaoImpl cardStatusJDBCDao = CardStatusJDBCDaoImpl.getInstance();
        PaymentSystemJDBCDaoImpl paymentSystemJDBCDao = PaymentSystemJDBCDaoImpl.getInstance();
        CurrencyJDBCDaoImpl currencyJDBCDao = CurrencyJDBCDaoImpl.getInstance();
        IssuingBankJDBCDaoImpl issuingBankJDBCDao = IssuingBankJDBCDaoImpl.getInstance();
        AccountJDBCDaoImpl accountJDBCDao = AccountJDBCDaoImpl.getInstance();
        CardJDBCDaoImpl cardJDBCDao = CardJDBCDaoImpl.getInstance();

//        Удалим старые таблицы
        cardJDBCDao.deleteTable();
        accountJDBCDao.deleteTable();
        issuingBankJDBCDao.deleteTable();
        currencyJDBCDao.deleteTable();
        paymentSystemJDBCDao.deleteTable();
        cardStatusJDBCDao.deleteTable();

//        Создадим таблицы
        cardStatusJDBCDao.createTable();
        paymentSystemJDBCDao.createTable();
        issuingBankJDBCDao.createTable();
        currencyJDBCDao.createTable();
        accountJDBCDao.createTable();
        cardJDBCDao.createTable();

//        Создадим статусы карт
        CardStatus cardStatus = new CardStatus(1L, "Card is valid.");
        CardStatus cardStatus2 = new CardStatus(1L, "Card is lost.");
        cardStatusJDBCDao.save(cardStatus);
        cardStatusJDBCDao.save(cardStatus2);

//        Создадим платежные системы
        PaymentSystem paymentSystem = new PaymentSystem(1L, "MasterCard");
        PaymentSystem paymentSystem2 = new PaymentSystem(1L, "VISA");
        paymentSystemJDBCDao.save(paymentSystem);
        paymentSystemJDBCDao.save(paymentSystem2);

//        Создадим валюты
        Currency currency = new Currency(1L, "643", "RUB", "Russian Ruble");
        Currency currency2 = new Currency(1L, "978", "EUR", "Euro");
        currencyJDBCDao.save(currency);
        currencyJDBCDao.save(currency2);

//        Создадим банки
        IssuingBank issuingBank = new IssuingBank(1L, "041234569", "12345", "ПАО - Банк-эмитент №1");
        IssuingBank issuingBank2 = new IssuingBank(1L, "041234585", "12376", "ПАО - Банк-эмитент №2");
        issuingBankJDBCDao.save(issuingBank);
        issuingBankJDBCDao.save(issuingBank2);

//        Создадим счета
        Account account = new Account(1L, "40817810800000000001", BigDecimal.valueOf(649.7), currency, issuingBank);
        Account account2 = new Account(1L, "40817810800000000002", BigDecimal.valueOf(48702.7), currency2, issuingBank2);
        accountJDBCDao.save(account);
        accountJDBCDao.save(account2);

//        Создадим карты
        Card card1 = new Card(
                1L,
                "41034500000000000020",
                LocalDate.of(2024, 10, 30),
                "Иван Иванов",
                cardStatus,
                paymentSystem,
                account,
                LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175),
                LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271));

        Card card2 = new Card(
                1L,
                "41034500000000000021",
                LocalDate.of(2024, 9, 25),
                "Мария Петрова",
                cardStatus2,
                paymentSystem2,
                account2,
                LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175),
                LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271));

        Card card3 = new Card(
                1L,
                "41034500000000000022",
                LocalDate.of(2024, 8, 20),
                "Алексей Смирнов",
                cardStatus,
                paymentSystem2,
                account,
                LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175),
                LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271));

        Card card4 = new Card(
                1L,
                "41034500000000000023",
                LocalDate.of(2024, 7, 15),
                "Елена Кузнецова",
                cardStatus2,
                paymentSystem,
                account2,
                LocalDateTime.of(2022, 10, 21, 15, 26, 6, 175),
                LocalDateTime.of(2022, 10, 21, 15, 27, 8, 271));
//        Добавим в базу счета
        cardJDBCDao.save(card1);
        cardJDBCDao.save(card2);
        cardJDBCDao.save(card3);
        cardJDBCDao.save(card4);

//        Обновим карты в базе
        cardJDBCDao.findAll().forEach(System.out::println);

//        Изменим карты
        card1.setHolderName("Анна Сидорова");
        card2.setHolderName("Игорь Соловьёв");

//        Обновим карты в базе
        cardJDBCDao.update(card1);
        cardJDBCDao.update(card2);

//        Выведем все карты
        cardJDBCDao.findAll().forEach(System.out::println);

//        Удалим платежную систему и статус карты
        paymentSystemJDBCDao.delete(1L);
        cardStatusJDBCDao.delete(1L);

//        Выведем все карты
        cardJDBCDao.findAll().forEach(System.out::println);

//        Очистим таблицу
//        cardJDBCDao.deleteAllFromTable();

//        Удалить таблицу
//        cardJDBCDao.deleteTable();
    }
}

